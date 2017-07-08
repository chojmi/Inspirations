package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.photos.PhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemotePhotosDataSourceTest {
    private static final String FAKE_PHOTO_ID = "123";
    private final Map<String, String> fakeQueryMap = new HashMap<>();
    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    @Mock private PhotosService mockPhotoService;
    private TestSubscriber testSubscriber;
    private RemotePhotosDataSource remotePhotosDataSource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() throws Exception {
        this.remotePhotosDataSource = new RemotePhotosDataSource(mockPhotoService, mockRemoteQueryProducer);
        this.testSubscriber = new TestSubscriber();
    }

    @Test
    public void loadPhotoCommentsHappyCase() {
        final PhotoCommentsEntityImpl mockPhotoCommentsEntity = Mockito.mock(PhotoCommentsEntityImpl.class);
        when(mockRemoteQueryProducer.produceLoadPhotoComments(FAKE_PHOTO_ID)).thenReturn(fakeQueryMap);
        when(mockPhotoService.loadPhotoComments(fakeQueryMap)).thenReturn(Flowable.just(mockPhotoCommentsEntity));

        remotePhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID).subscribe(testSubscriber);

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(mockPhotoCommentsEntity);
        testSubscriber.assertComplete();
    }

    @Test
    public void loadPhotoFavsHappyCase() {
        final PhotoFavsEntityImpl mockPhotoFavsEntity = Mockito.mock(PhotoFavsEntityImpl.class);
        when(mockRemoteQueryProducer.produceLoadPhotoFavsQuery(FAKE_PHOTO_ID)).thenReturn(fakeQueryMap);
        when(mockPhotoService.loadPhotoFavs(fakeQueryMap)).thenReturn(Flowable.just(mockPhotoFavsEntity));

        remotePhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID).subscribe(testSubscriber);

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(mockPhotoFavsEntity);
        testSubscriber.assertComplete();
    }
}