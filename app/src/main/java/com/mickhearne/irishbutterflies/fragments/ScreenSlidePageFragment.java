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

import com.mickhearne.irishbutterflies.MainActivity;
import com.mickhearne.irishbutterflies.R;


public class ScreenSlidePageFragment extends Fragment {


    public static final String ARG_TITLE = "title";
    public static final String ARG_PAGE = "page";
    public static final String ARG_NAME = "name";


    /**
     * The fragment's title, which is set to the argument value for {@link #ARG_TITLE}.
     */
    private String title;


    /**
     * The fragment's page name, which is set to the argument value for {@link #ARG_NAME}.
     */
    private String name;


    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;


    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static Fragment newInstance(String imageName, int position, String title) {
        Fragment fragment = new ScreenSlidePageFragment();

        Bundle args = new Bundle();
        args.putString(ARG_NAME, imageName);
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_PAGE, position);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(ARG_TITLE);
        name = getArguments().getString(ARG_NAME);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.slide_show_image);

        int imageResource = getResources().getIdentifier(name + "_" + (mPageNumber + 1),
                "drawable", getActivity().getPackageName());
        if (imageResource != 0) {
            imageView.setImageResource(imageResource);
        }

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle(title);
        }
    }
}