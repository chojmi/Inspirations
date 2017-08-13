package com.github.chojmi.inspirations.presentation.profile.user_profile;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileView extends LinearLayoutCompat implements UserProfileContract.View {
    @Inject UserProfileContract.Presenter presenter;
    @BindView(R.id.user_name) TextView userName;
    @BindView(R.id.user_description) TextView description;
    @BindView(R.id.user_public_photos) UserPublicPhotosView userPublicPhotosView;

    public UserProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (presenter != null) {
            presenter.destroyView();
        }
    }

    @Override
    public void renderProfile(PersonEntity personEntity) {
        userName.setText(personEntity.getUsername());
    }

    public void setUserProfileComponent(UserProfileComponent userProfileComponent) {
        userProfileComponent.inject(userPublicPhotosView);
        userProfileComponent.inject(this);
        presenter.setView(this);
        userPublicPhotosView.refresh();
    }
}
