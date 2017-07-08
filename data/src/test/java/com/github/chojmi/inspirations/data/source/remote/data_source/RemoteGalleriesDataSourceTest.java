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

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteGalleriesDataSourceTest {
    private static final String FAKE_GALLERY_ID = "123";
    private final Map<String, String> fakeQueryMap = new HashMap<>();
    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    @Mock private GalleriesService mockGalleryService;
    private TestSubscriber testSubscriber;
    private RemoteGalleriesDataSource remoteGalleriesDataSource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() {
        this.remoteGalleriesDataSource = new RemoteGalleriesDataSource(mockGalleryService, mockRemoteQueryProducer);
        this.testSubscriber = new TestSubscriber();
    }

    @Test
    public void loadGalleryHappyCase() {
        List<PhotoEntityImpl> result = new ArrayList<>();
        GalleryEntityImpl mockGalleryEntityImpl = Mockito.mock(GalleryEntityImpl.class);
        when(mockRemoteQueryProducer.produceLoadGalleryQuery(FAKE_GALLERY_ID, 1)).thenReturn(fakeQueryMap);
        when(mockGalleryService.loadGallery(fakeQueryMap)).thenReturn(Flowable.just(mockGalleryEntityImpl));
        when(mockGalleryEntityImpl.getPhoto()).thenReturn(result);

        remoteGalleriesDataSource.loadGallery(FAKE_GALLERY_ID).subscribe(testSubscriber);

        testSubscriber.assertSubscribed();
        testSubscriber.assertResult(result);
        testSubscriber.assertComplete();
    }
}