package com.example.android.miwok;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordListFragment extends Fragment {
    /** The {@link WordAdapter} to handle word audio playback stuff */
    WordAdapter mWordAdapter;


    public WordListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_recycler, container, false);

        ArrayList<Word> words = getArguments().getParcelableArrayList("words");
        int itemBgColorResId = getArguments().getInt("itemBgColorResId");

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mWordAdapter =
                new WordAdapter(recyclerView, words, itemBgColorResId);
        recyclerView.setAdapter(mWordAdapter);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        mWordAdapter.listItemClickHandler.releaseMediaPlayerAndAudioFocus();
    }
}
