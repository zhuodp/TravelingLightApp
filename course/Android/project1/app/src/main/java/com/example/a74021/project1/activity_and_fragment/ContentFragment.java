package com.example.a74021.project1.activity_and_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a74021.project1.R;

/**
 * Created by 74021 on 2018/1/6.
 */

public class ContentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        return inflater.inflate(R.layout.fragment_content,container,false);

    }
}
