package com.github.chojmi.inspirations.presentation.profile.user_profile;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.profile.user_profile.tabs.UserProfileSlidePagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileView extends CoordinatorLayout implements UserProfileContract.View {
    @Inject UserProfileContract.Presenter presenter;
    @BindView(R.id.user_name) TextView userName;
    @BindView(R.id.user_description) TextView description;
    @BindView(R.id.pager) ViewPager pager;

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
        description.setText("Not implemented yet");
    }

    public void setUserProfileComponent(UserProfileComponent userProfileComponent) {
        presenter.setView(this);
        pager.setAdapter(new UserProfileSlidePagerAdapter(getContext(), userProfileComponent));
    }
}
