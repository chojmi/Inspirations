package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class GetPhotoFavsTest {
    private static final String FAKE_PHOTO_ID = "234";
    private static final String FAKE_USER_ID = "123";
    private static final String FAKE_USERNAME = "fake_username";
    private static final String FAKE_ICON_URL = "fake_icon_url";

    private GetPhotoFavs getPhotoFavs;
    private TestObserver testObserver;
    @Mock
    private PhotosDataSource mockPhotosDataSource;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() throws Exception {
        getPhotoFavs = new GetPhotoFavs(mockPhotosDataSource, mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestObserver();
    }

    @Test
    public void shouldInvokeInProgressEventAtBeginning() {
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.empty());
        Observable<GetPhotoFavs.SubmitUiModel> resultObs = getPhotoFavs.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoFavs.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoFavs.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        final List<PersonEntity> resultFavs = createFakeFavsList();
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.fromCallable(() -> resultFavs));
        Observable<GetPhotoFavs.SubmitUiModel> resultObs = getPhotoFavs.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoFavs.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoFavs.SubmitUiModel.inProgress(), GetPhotoFavs.SubmitUiModel.success(resultFavs));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_USER_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetPhotoFavs.SubmitUiModel> resultObs = getPhotoFavs.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoFavs.SubmitEvent.create(FAKE_USER_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoFavs.SubmitUiModel.inProgress(), GetPhotoFavs.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }

    private List<PersonEntity> createFakeFavsList() {
        List<PersonEntity> fakePhotoEntities = new ArrayList<>();
        fakePhotoEntities.add(new FakePersonEntity());
        return fakePhotoEntities;
    }

    private class FakePersonEntity implements PersonEntity {

        @Override
        public String getUsername() {
            return FAKE_USERNAME;
        }

        @Override
        public String getIconUrl() {
            return FAKE_ICON_URL;
        }
    }
}