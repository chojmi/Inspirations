package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
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
public class GetPhotoCommentsTest {
    private static final String FAKE_PHOTO_ID = "123";
    private static final String FAKE_AUTHORNAME = "fake_authorname";
    private static final String FAKE_CONTENT = "fake_content";

    private GetPhotoComments getPhotoComments;
    private TestObserver testObserver;
    @Mock
    private PhotosDataSource mockPhotosDataSource;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() throws Exception {
        getPhotoComments = new GetPhotoComments(mockPhotosDataSource, mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestObserver();
    }

    @Test
    public void shouldInvokeInProgressEventAtBeginning() {
        Mockito.when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.empty());
        Observable<GetPhotoComments.SubmitUiModel> resultObs = getPhotoComments.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoComments.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoComments.SubmitUiModel.inProgress());
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnProperValue() {
        final PhotoCommentsEntity photoCommentsEntity = createFakePhotoCommentsEntity();
        Mockito.when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.fromCallable(() -> photoCommentsEntity));
        Observable<GetPhotoComments.SubmitUiModel> resultObs = getPhotoComments.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoComments.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoComments.SubmitUiModel.inProgress(), GetPhotoComments.SubmitUiModel.success(photoCommentsEntity));
        resultObs.test().assertComplete();
    }

    @Test
    public void shouldReturnError() {
        Throwable fakeThrowable = new Throwable("Fake throwable");
        Mockito.when(mockPhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID)).thenReturn(Observable.error(fakeThrowable));
        Observable<GetPhotoComments.SubmitUiModel> resultObs = getPhotoComments.buildUseCaseObservable(Observable.fromCallable(() -> GetPhotoComments.SubmitEvent.create(FAKE_PHOTO_ID)));
        testObserver.assertNotSubscribed();
        resultObs.subscribe(testObserver);
        testObserver.assertSubscribed();
        resultObs.test().assertSubscribed();
        resultObs.test().assertResult(GetPhotoComments.SubmitUiModel.inProgress(), GetPhotoComments.SubmitUiModel.failure(fakeThrowable));
        resultObs.test().assertComplete();
    }

    private PhotoCommentsEntity createFakePhotoCommentsEntity() {
        return new PhotoCommentsEntity() {
            @Override
            public List<CommentEntity> getComments() {
                return createFakeCommentsList();
            }

            @Override
            public String getPhotoId() {
                return FAKE_PHOTO_ID;
            }
        };
    }

    private List<CommentEntity> createFakeCommentsList() {
        List<CommentEntity> fakeCommentsList = new ArrayList<>();
        fakeCommentsList.add(new FakeCommentEntity());
        return fakeCommentsList;
    }

    private class FakeCommentEntity implements CommentEntity {

        @Override
        public String getAuthorname() {
            return FAKE_AUTHORNAME;
        }

        @Override
        public String getContent() {
            return FAKE_CONTENT;
        }
    }
}