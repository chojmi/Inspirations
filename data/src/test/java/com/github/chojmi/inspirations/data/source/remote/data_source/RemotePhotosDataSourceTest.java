package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.photos.PhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.PhotoFavsEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemotePhotosDataSourceTest {
    private static final String FAKE_PHOTO_ID = "123";
    private static final String FAKE_QUERY = "fake_query";
    private final Map<String, String> fakeQueryMap = new HashMap<>();
    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    @Mock private PhotosService mockPhotoService;
    private TestObserver testObserver;
    private RemotePhotosDataSource remotePhotosDataSource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() throws Exception {
        this.remotePhotosDataSource = new RemotePhotosDataSource(mockPhotoService, mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }

    @Test
    public void loadPhotoCommentsHappyCase() {
        final PhotoCommentsEntityImpl mockPhotoCommentsEntity = Mockito.mock(PhotoCommentsEntityImpl.class);
        when(mockRemoteQueryProducer.produceLoadPhotoComments(FAKE_PHOTO_ID)).thenReturn(fakeQueryMap);
        when(mockPhotoService.loadPhotoComments(fakeQueryMap)).thenReturn(Observable.just(mockPhotoCommentsEntity));

        remotePhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(mockPhotoCommentsEntity);
        testObserver.assertComplete();
    }

    @Test
    public void loadPhotoFavsHappyCase() {
        final PhotoFavsEntityImpl mockPhotoFavsEntity = Mockito.mock(PhotoFavsEntityImpl.class);
        when(mockRemoteQueryProducer.produceLoadPhotoFavsQuery(FAKE_PHOTO_ID)).thenReturn(fakeQueryMap);
        when(mockPhotoService.loadPhotoFavs(fakeQueryMap)).thenReturn(Observable.just(mockPhotoFavsEntity));

        remotePhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(mockPhotoFavsEntity);
        testObserver.assertComplete();
    }

    @Test
    public void loadPhotoSearchHappyCase() {
        final GalleryEntity mockGalleryEntity = Mockito.mock(GalleryEntity.class);
        when(mockRemoteQueryProducer.produceLoadSearchPhoto(FAKE_QUERY)).thenReturn(fakeQueryMap);
        when(mockPhotoService.loadSearchPhoto(fakeQueryMap)).thenReturn(Observable.just(mockGalleryEntity));

        remotePhotosDataSource.loadSearchPhoto(FAKE_QUERY).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(mockGalleryEntity);
        testObserver.assertComplete();
    }
}