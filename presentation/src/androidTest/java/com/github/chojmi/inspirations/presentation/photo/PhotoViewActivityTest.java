package com.github.chojmi.inspirations.presentation.photo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.chojmi.inspirations.presentation.InspirationsApp;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.main.Navigator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PhotoViewActivityTest {
    private final Photo fakePhoto = new Photo("FAKE_ID", "FAKE_TITLE", "FAKE_URL", "FAKE_OWNER_ID");
    @Rule
    public ActivityTestRule<PhotoViewActivity> mActivityRule =
            new ActivityTestRule<>(PhotoViewActivity.class, true, false);
    @Mock private Navigator mockNavigator;
    @Mock private PhotoViewContract.Presenter mockPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ((InspirationsApp) targetContext.getApplicationContext())
                .setActivityInjector(instance -> ((PhotoViewActivity) instance).presenter = mockPresenter);
        ((InspirationsApp) targetContext.getApplicationContext()).setNavigator(mockNavigator);
        mActivityRule.launchActivity(PhotoViewActivity.getCallingIntent(targetContext, fakePhoto));
    }

    @Test
    public void testGetPhoto() {
        Assert.assertEquals(fakePhoto, mActivityRule.getActivity().getPhoto());
    }

    @Test
    public void testDisplayedTitle() {
        mActivityRule.getActivity().runOnUiThread(() -> mActivityRule.getActivity().showPhoto(fakePhoto));

        Espresso.onView(ViewMatchers.withId(R.id.title)).check(matches(withText(fakePhoto.getTitle())));
    }

    @Test
    public void testOnPhotoOwnerClick() {
        mActivityRule.getActivity().onPhotoOwnerClick(null);

        Mockito.verify(mockNavigator, Mockito.times(1))
                .navigateToUserProfile(mActivityRule.getActivity(), fakePhoto.getOwnerId());
    }

    @Test
    public void testGoToFavs() {
        mActivityRule.getActivity().goToFavs(fakePhoto);

        Mockito.verify(mockNavigator, Mockito.times(1))
                .navigateToPhotoFavs(mActivityRule.getActivity(), fakePhoto.getId());
    }

    @Test
    public void testPresenterActions() {
        Mockito.verify(mockPresenter, Mockito.times(1)).setView(mActivityRule.getActivity());
        Mockito.verify(mockPresenter, Mockito.never()).destroyView();

        mActivityRule.getActivity().finish();

        Mockito.verify(mockPresenter, Mockito.times(1)).destroyView();
    }

    @Test
    public void testClickOnFavIcon() {
        Espresso.onView(ViewMatchers.withId(R.id.favs_icon)).perform(click());

        Mockito.verify(mockPresenter, Mockito.times(1)).favIconSelected(false);
    }
}