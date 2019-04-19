package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * This handler is for playing corresponding audio for each list item and related stuff. <br />
 * You may want to call {@link #releaseMediaPlayerAndAbandonAudioFocus()} in your
 * {@link Activity#onStop()} or {@link Activity#onPause()}.
 */
public class ListItemClickHandler implements AdapterView.OnItemClickListener {
    /** The current context */
    private Context mContext;
    /** List of {@link Word} */
    private List<Word> mWords;
    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;
    /** For handling corresponding Audio Focus */
    private AudioManager mAudioManager;

    /**
     * Constructor
     * @param context The current context.
     * @param words   List of {@link Word} which contains audio resource ID.
     */
    public ListItemClickHandler(Context context, List<Word> words) {
        this.mContext = context;
        this.mWords = words;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Release the media player if it currently exists
        releaseMediaPlayer();

        // Request Audio Focus
        int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // Start playback if granted Audio Focus.
            int audioResId = mWords.get(position).getAudioResId();
            mMediaPlayer = MediaPlayer.create(mContext, audioResId);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        }
    }

    /**
     * Release {@link #mMediaPlayer}'s resources & corresponding Audio Focus.
     */
    void releaseMediaPlayerAndAbandonAudioFocus() {
        releaseMediaPlayer();
        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
    }

    /**
     * This listener releases {@link #mMediaPlayer}'s resources & corresponding Audio Focus after
     * completed playback, gets triggered when the {@link #mMediaPlayer} has completed playing
     * the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    };

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0); // To play the word from start when resuming.
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback, because you lost the Audio Focus.
                        mMediaPlayer.release();
                        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback, because you hold the Audio Focus again!
                        mMediaPlayer.start();
                    }
                }
            };

    /**
     * Clean up the {@link #mMediaPlayer} by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}
