package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * This handler is for playing corresponding audio for each list item and related stuff
 * for {@link WordAdapter}. <br />
 * You may want to call {@link #releaseMediaPlayerAndAudioFocus()} in your
 * {@link Activity#onStop()} or {@link Activity#onPause()}.
 */
public class ListItemClickHandler implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    /** List of {@link Word} */
    private List<Word> mWords;
    /** Handles playback of all the sound files */
    private static MediaPlayer mMediaPlayer;
    /** For handling corresponding Audio Focus */
    private static AudioManager mAudioManager;

    /**
     * Constructor
     * @param recyclerView is {@link RecyclerView} of the {@link WordAdapter} instance.
     * @param words        is the List of {@link Word} which contains audio resource ID.
     */
    public ListItemClickHandler(RecyclerView recyclerView, List<Word> words) {
        this.mRecyclerView = recyclerView;
        this.mWords = words;
        mAudioManager =
                (AudioManager) recyclerView.getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onClick(View v) {
        // Release the media player & audio focus if it currently exists
        releaseMediaPlayerAndAudioFocus();

        // Request Audio Focus
        int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // Start playback if granted Audio Focus.
            int audioResId = mWords.get(mRecyclerView.getChildAdapterPosition(v)).getAudioResId();
            mMediaPlayer = MediaPlayer.create(v.getContext(), audioResId);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        }
    }

    /**
     * Release {@link #mMediaPlayer}'s resources & corresponding Audio Focus.
     */
    public void releaseMediaPlayerAndAudioFocus() {
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
    private static AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener =
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
