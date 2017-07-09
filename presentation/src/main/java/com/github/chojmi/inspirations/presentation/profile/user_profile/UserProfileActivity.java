package com.github.chojmi.inspirations.presentation.profile.user_profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.chojmi.inspirations.domain.utils.Preconditions;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseActivity;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

public class UserProfileActivity extends BaseActivity {
    private static final String ARG_USER_ID = "ARG_USER_ID";
    @BindView(R.id.user_profile_view) UserProfileView userProfileView;

    public static Intent getCallingIntent(Context context, @NonNull String userId) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra(ARG_USER_ID, Preconditions.checkNotNull(userId));
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        UserProfileComponent userProfileComponent = getInspirationsApp().createUserProfileComponent(getIntent().getStringExtra(ARG_USER_ID));
        userProfileComponent.inject(userProfileView);
        userProfileView.setUserProfileComponent(userProfileComponent);
    }

    @Override
    protected void onDestroy() {
        getInspirationsApp().releaseUserProfileComponent();
        super.onDestroy();
    }
}
