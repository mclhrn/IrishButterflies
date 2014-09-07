/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mickhearne.irishbutterflies.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mickhearne.irishbutterflies.R;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class ScreenSlidePageFragment extends Fragment {


    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private String name;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment newInstance(String name) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(ARG_PAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(com.mickhearne.irishbutterflies.R.layout.fragment_screen_slide_page, container, false);

        // Set the title view to show the page number.
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText("Test" + mPageNumber + 1);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.slide_show_image);

        int imageResource = getResources().getIdentifier(name,
                "drawable", getActivity().getPackageName());
        if (imageResource != 0) {
            imageView.setImageResource(imageResource);
        }

        Log.i("butterfly", "should be an image of " + name);

        return rootView;
    }


    /**
     * Returns the page number represented by this fragment object.
     */
//    public int getPageNumber() {
//        return mPageNumber;
//    }
}