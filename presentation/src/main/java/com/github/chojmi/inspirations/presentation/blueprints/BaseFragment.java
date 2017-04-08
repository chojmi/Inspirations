package com.github.chojmi.inspirations.presentation.blueprints;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.chojmi.inspirations.presentation.InspirationsApp;
import com.github.chojmi.inspirations.presentation.navigation.Navigator;

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

    @Override
    public Navigator getNavigator() {
        V parentView = mParentView.get();
        if (parentView != null) {
            return parentView.getNavigator();
        }
        return null;
    }

    @Override
    public InspirationsApp getInspirationsApp() {
        V parentView = mParentView.get();
        if (parentView != null) {
            return parentView.getInspirationsApp();
        }
        return null;
    }
}
