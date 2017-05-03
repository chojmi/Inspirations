package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.fake_service.FakeGalleriesService;
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

import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class RemoteGalleriesDataSourceTest {
    private static final String FAKE_GALLERY_ID = "123";
    private static final String FAKE_GALLERY_ARG = "fake_gallery_arg";

    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    private RemoteGalleriesDataSource remoteGalleriesDataSource;
    private TestObserver testObserver;
    private FakeGalleriesService fakeGalleriesService;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() {
        this.fakeGalleriesService = new FakeGalleriesService(getFakeQueryMap());
        this.remoteGalleriesDataSource = new RemoteGalleriesDataSource(fakeGalleriesService, mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }

    @Test
    public void loadGalleryHappyCase() {
        Mockito.when(mockRemoteQueryProducer.produceLoadGalleryQuery(FAKE_GALLERY_ID, 1)).thenReturn(getFakeQueryMap());
        remoteGalleriesDataSource.loadGallery(FAKE_GALLERY_ID).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(fakeGalleriesService.getFakeGalleryEntity().getPhoto());
        testObserver.assertComplete();
    }

    private Map<String, String> getFakeQueryMap() {
        Map<String, String> args = new HashMap<>();
        args.put(FAKE_GALLERY_ARG, FAKE_GALLERY_ID);
        return args;
    }
}