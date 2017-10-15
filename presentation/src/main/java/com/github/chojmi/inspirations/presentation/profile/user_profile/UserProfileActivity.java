package com.github.chojmi.inspirations.presentation.profile.user_profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import io.reactivex.annotations.NonNull;

public class UserProfileActivity extends BaseActivity {
    private static final String ARG_USER_ID = "ARG_USER_ID";
    @Inject UserProfileContract.Presenter userProfilePresenter;
    @Inject UserPublicPhotosContract.Presenter userPublicPhotosPresenter;
    @BindView(R.id.user_public_photos) UserPublicPhotosView userPublicPhotosView;
    @BindView(R.id.user_profile_view) UserProfileView userProfileView;

    public static Intent getCallingIntent(Context context, @NonNull String userId) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra(ARG_USER_ID, Preconditions.checkNotNull(userId));
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_my_activity);
        userProfilePresenter.setView(userProfileView);
        userPublicPhotosPresenter.setView(userPublicPhotosView);
        userPublicPhotosView.getPhotoClicksObservable()
                .subscribe(pair -> getNavigator().navigateToPhoto(this, pair.second, pair.first));
    }

    public String getArgUserId() {
        return getIntent().getStringExtra(ARG_USER_ID);
    }
}
