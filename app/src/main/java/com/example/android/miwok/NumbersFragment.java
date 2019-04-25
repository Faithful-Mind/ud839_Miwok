package com.example.android.miwok;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
    /** The {@link WordRecyclerAdapter} to handle word audio playback stuff */
    WordRecyclerAdapter mWordRecyclerAdapter;


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_recycler, container, false);

        ArrayList<Word> words = getArguments().getParcelableArrayList("words");

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mWordRecyclerAdapter =
                new WordRecyclerAdapter(recyclerView, words, R.color.category_numbers);
        recyclerView.setAdapter(mWordRecyclerAdapter);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        mWordRecyclerAdapter.itemClickHandler.releaseMediaPlayerAndAudioFocus();
    }
}
