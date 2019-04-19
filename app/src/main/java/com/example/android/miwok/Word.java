package com.example.android.miwok;

/**
 * <code>Word</code> represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {
    /** Default translation for the word */
    private String mDefaultTranslation;
    /** Miwok translation for the word */
    private String mMiwokTranslation;
    /** Image resource ID for the word */
    private int mImageResId = NO_IMAGE_PROVIDED;
    /** Audio resource ID for the word in miwok */
    private int mAudioResId;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Create a new {@link Word} object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     */
    public Word(String defaultTranslation, String miwokTranslation) {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
    }

    /**
     * Create a new {@link Word} object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     * @param audioResId        is the audio resource ID for the word in miwok
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResId) {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.mAudioResId = audioResId;
    }

    /**
     * Create a new {@link Word} object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     * @param imageResId         is the drawable resource ID for the image associated with the word
     * @param audioResId        is the audio resource ID for the word in miwok
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResId, int audioResId) {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.mImageResId = imageResId;
        this.mAudioResId = audioResId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResId != NO_IMAGE_PROVIDED;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public int getAudioResId() {
        return mAudioResId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResId=" + mImageResId +
                ", mAudioResId=" + mAudioResId +
                '}';
    }
}
