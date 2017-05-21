package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
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
    private static final String FAKE_PHOTO_ID = "123";
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
        final PhotoFavsEntity photoFavsEntity = createFakePhotoFavsEntity();
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.fromCallable(() -> photoFavsEntity));
        Observable<GetPhotoFavs.SubmitUiModel> resultObs = getPhotoFavs.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoFavs.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoFavs.SubmitUiModel.inProgress(), GetPhotoFavs.SubmitUiModel.success(photoFavsEntity));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetPhotoFavs.SubmitUiModel> resultObs = getPhotoFavs.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoFavs.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoFavs.SubmitUiModel.inProgress(), GetPhotoFavs.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }

    private PhotoFavsEntity createFakePhotoFavsEntity() {
        return new PhotoFavsEntity<PersonEntity>() {
            @Override
            public List<PersonEntity> getPeople() {
                return createFakeFavsList();
            }

            @Override
            public int getPage() {
                return 0;
            }

            @Override
            public String getPhotoId() {
                return null;
            }

            @Override
            public int getTotal() {
                return 0;
            }
        };
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