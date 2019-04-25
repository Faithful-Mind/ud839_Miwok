package com.example.android.miwok;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * <code>WordAdapter</code> is an {@link RecyclerView.Adapter} that can provide the layout for each list item
 * based on a data source, which is a {@link List} of {@link Word} objects.
 */
public class WordAdapter extends RecyclerView.Adapter {
    /** List of {@link Word} */
    private List<Word> mWords;
    /** Resource ID for the background color for each item in this list of words */
    private int mItemBgColorResId = NOT_PROVIDED;

    ListItemClickHandler listItemClickHandler;


    private static final int NOT_PROVIDED = -1;

    public static class WordViewHolder extends RecyclerView.ViewHolder {

        public WordViewHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * Constructor
     * @param recyclerView is {@link RecyclerView} fot the {@link ListItemClickHandler}.
     * @param words        is the list of {@link Word}s to be displayed.
     */
    public WordAdapter(RecyclerView recyclerView, List<Word> words) {
        this.mWords = words;

        listItemClickHandler = new ListItemClickHandler(recyclerView, mWords);
    }

    /**
     * Constructor
     * @param recyclerView     is {@link RecyclerView} fot the {@link ListItemClickHandler}.
     * @param words            is the list of {@link Word}s to be displayed.
     * @param itemBgColorResId is the resource ID for the background color for each item in
     *                         this list of words.
     */
    public WordAdapter(RecyclerView recyclerView, List<Word> words, int itemBgColorResId) {
        this(recyclerView, words);
        this.mItemBgColorResId = itemBgColorResId;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        listItemView.setOnClickListener(listItemClickHandler);
        RecyclerView.ViewHolder vh = new WordViewHolder(listItemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View listItemView = holder.itemView;
        Word currentWord = mWords.get(position);

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Hide image if absent
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResId());
            imageView.setVisibility(View.VISIBLE); // Make sure the view is visible
        } else {
            imageView.setVisibility(View.GONE);
        }

        // Set the theme color for the list item
        if (mItemBgColorResId != NOT_PROVIDED) {
            View textContainerView = listItemView.findViewById(R.id.text_container);
            textContainerView.setBackgroundColor(
                    // Convert color resource ID to color integer
                    ContextCompat.getColor(listItemView.getContext(), mItemBgColorResId));
        }
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }
}
