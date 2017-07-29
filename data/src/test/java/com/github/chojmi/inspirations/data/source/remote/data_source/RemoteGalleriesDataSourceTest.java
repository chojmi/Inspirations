package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.PhotoEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.GalleriesService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

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
        List<PhotoEntityImpl> result = new ArrayList<>();
        GalleryEntityImpl mockGalleryEntityImpl = Mockito.mock(GalleryEntityImpl.class);
        when(mockRemoteQueryProducer.produceLoadGalleryByGalleryIdQuery(FAKE_GALLERY_ID, 1)).thenReturn(fakeQueryMap);
        when(mockGalleryService.loadGallery(fakeQueryMap)).thenReturn(Observable.just(mockGalleryEntityImpl));
        when(mockGalleryEntityImpl.getPhoto()).thenReturn(result);

        remoteGalleriesDataSource.loadGalleryByGalleryId(FAKE_GALLERY_ID).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(result);
        testObserver.assertComplete();
    }
}