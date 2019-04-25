package com.example.android.miwok;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CategoryPagerAdapter extends FragmentPagerAdapter {
    /** Context of the app */
    private Context mContext;


    public CategoryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle args = new Bundle();
                ArrayList<Word> words = new ArrayList<Word>();
                words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
                words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
                words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
                words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
                words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
                words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
                words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
                words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
                words.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
                words.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

                args.putParcelableArrayList("words", words);
                NumbersFragment numbersFragment = new NumbersFragment();
                numbersFragment.setArguments(args);

                return numbersFragment;
            case 1: return new FamilyFragment();
            case 2: return new ColorsFragment();
            default:
            case 3: return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return mContext.getString(R.string.category_numbers);
            case 1: return mContext.getString(R.string.category_family);
            case 2: return mContext.getString(R.string.category_colors);
            default:
            case 3: return mContext.getString(R.string.category_phrases);
        }
    }
}
