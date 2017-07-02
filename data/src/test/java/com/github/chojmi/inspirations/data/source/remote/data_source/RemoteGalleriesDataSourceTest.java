package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.GalleriesService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class RemoteGalleriesDataSourceTest {
    private static final String FAKE_GALLERY_ID = "123";
    private final Map<String, String> fakeQueryMap = new HashMap<>();
    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    @Mock private GalleriesService mockGalleryService;
    private TestObserver testObserver;
    private RemoteGalleriesDataSource remoteGalleriesDataSource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() {
        this.remoteGalleriesDataSource = new RemoteGalleriesDataSource(mockGalleryService, mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }

    @Test
    public void loadGalleryHappyCase() {
//        List<PhotoEntityImpl> result = new ArrayList<>();
//        GalleryEntityImpl mockGalleryEntityImpl = Mockito.mock(GalleryEntityImpl.class);
//        when(mockRemoteQueryProducer.produceLoadGalleryQuery(FAKE_GALLERY_ID, 1)).thenReturn(fakeQueryMap);
//        when(mockGalleryService.loadGallery(fakeQueryMap)).thenReturn(Flowable.just(mockGalleryEntityImpl));
//        when(mockGalleryEntityImpl.getPhoto()).thenReturn(result);
//
//        remoteGalleriesDataSource.loadGallery(FAKE_GALLERY_ID).subscribe(testObserver);
//
//        testObserver.assertSubscribed();
//        testObserver.assertResult(result);
//        testObserver.assertComplete();
    }
}