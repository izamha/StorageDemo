package com.assignment.storagedemo.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.assignment.storagedemo.PlaceHolderFragment;

public class SwipeViewAdapter extends FragmentStateAdapter {
    public SwipeViewAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance with this method
        Fragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(PlaceHolderFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
