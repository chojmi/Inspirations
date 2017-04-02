package com.github.chojmi.inspirations.presentation.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseFragment;
import com.github.chojmi.inspirations.presentation.main.MainActivity;

import butterknife.ButterKnife;

public class CameraFragment extends BaseFragment<MainActivity> {

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
}
