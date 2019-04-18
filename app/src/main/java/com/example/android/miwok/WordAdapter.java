package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * <code>WordAdapter</code> is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Word} objects.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    /** Resource ID for the background color for each item in this list of words */
    private int mItemBackgroundColorResId = NO_COLOR_PROVIDED;
    private static final int NO_COLOR_PROVIDED = -1;

    /**
     * Create a new {@link WordAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param words   is the list of {@link Word}s to be displayed.
     */
    public WordAdapter(Context context, List<Word> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
    }

    /**
     * Create a new {@link WordAdapter} object.
     *
     * @param context                   is the current context (i.e. Activity) that the adapter is
     *                                  being created in.
     * @param words                     is the list of {@link Word}s to be displayed.
     * @param itemBackgroundColorResId is the resource ID for the background color for each item in
     *                                 this list of words.
     */
    public WordAdapter(Context context, List<Word> words, int itemBackgroundColorResId) {
        super(context, 0, words);
        this.mItemBackgroundColorResId = itemBackgroundColorResId;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Hide image if absent
        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResId());
            imageView.setVisibility(View.VISIBLE); // Make sure the view is visible
        } else {
            imageView.setVisibility(View.GONE);
        }

        // Set the theme color for the list item
        if (mItemBackgroundColorResId != NO_COLOR_PROVIDED) {
            View textContainerView = listItemView.findViewById(R.id.text_container);
            textContainerView.setBackgroundColor(
                    // Convert color resource ID to color integer
                    ContextCompat.getColor(getContext(), mItemBackgroundColorResId));
        }

        return listItemView;
    }
}
