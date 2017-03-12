package com.github.chojmi.inspirations.presentation.blueprints;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public abstract class BaseFragment<V extends IBaseUI> extends Fragment implements IBaseUI {

    private WeakReference<V> mParentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            @SuppressWarnings("unchecked")
            V activity = (V) getActivity();
            mParentView = new WeakReference<>(activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must extend IBaseUI.");
        }
    }
}
