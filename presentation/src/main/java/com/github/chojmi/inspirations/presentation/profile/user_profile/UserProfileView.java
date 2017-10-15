package com.github.chojmi.inspirations.presentation.profile.user_profile;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.utils.ImageViewUtils;
import com.github.chojmi.inspirations.presentation.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.clearImageCache;

public class UserProfileView extends CoordinatorLayout implements UserProfileContract.View {
    @BindView(R.id.back) ImageButton backBtn;
    @BindView(R.id.user_name) TextView userName;
    @BindView(R.id.person_icon) ImageView personIcon;

    public UserProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.profile_user_view, this);
        ButterKnife.bind(this);
        initAttrsValues(context, attrs);
    }

    private void initAttrsValues(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UserProfileView, 0, 0);
        ViewUtils.setVisibility(backBtn, a.getBoolean(R.styleable.UserProfileView_showBackBtn, false));
        a.recycle();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearImageCache(personIcon);
    }

    @Override
    public void renderProfile(PersonEntity personEntity) {
        userName.setText(personEntity.getUsername());
        ImageViewUtils.loadImage(personIcon, personEntity.getIconUrl());
    }

    @OnClick(R.id.back)
    public void onBackClick(View view) {
        ((AppCompatActivity) getContext()).onBackPressed();
    }
}
