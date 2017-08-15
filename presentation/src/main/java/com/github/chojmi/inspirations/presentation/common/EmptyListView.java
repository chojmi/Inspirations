package com.github.chojmi.inspirations.presentation.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.github.chojmi.inspirations.presentation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyListView extends LinearLayoutCompat {
    @BindView(R.id.empty_list_text) TextView emptyListText;

    public EmptyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.common_empty_list, this);
        ButterKnife.bind(this);
        initAttrsValues(context, attrs);
    }

    private void initAttrsValues(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EmptyListView, 0, 0);
        emptyListText.setText(a.getString(R.styleable.EmptyListView_text));
    }
}
