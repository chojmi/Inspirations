package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.remote.test_service.FakePhotosService;
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
public class RemotePhotosDataSourceTest {
    private static final String FAKE_PHOTO_ID = "123";
    private static final String FAKE_PHOTO_ARG = "fake_photo_arg";

    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    private RemotePhotosDataSource remotePhotosDataSource;
    private TestObserver testObserver;
    private FakePhotosService fakePhotosService;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() throws Exception {
        this.fakePhotosService = new FakePhotosService(getFakePhotoIdQueryMap(), getFakePhotoIdQueryMap());
        this.remotePhotosDataSource = new RemotePhotosDataSource(fakePhotosService, mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }


    @Test
    public void loadPhotoCommentsHappyCase() {
        Mockito.when(mockRemoteQueryProducer.produceLoadPhotoComments(FAKE_PHOTO_ID)).thenReturn(getFakePhotoIdQueryMap());
        remotePhotosDataSource.loadPhotoComments(FAKE_PHOTO_ID).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(fakePhotosService.getLoadPhotoCommentsResult());
        testObserver.assertComplete();
    }

    @Test
    public void loadPhotoFavsHappyCase() {
        Mockito.when(mockRemoteQueryProducer.produceLoadPhotoFavsQuery(FAKE_PHOTO_ID)).thenReturn(getFakePhotoIdQueryMap());
        remotePhotosDataSource.loadPhotoFavs(FAKE_PHOTO_ID).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(fakePhotosService.getLoadPhotoFavsResult());
        testObserver.assertComplete();
    }

    private Map<String, String> getFakePhotoIdQueryMap() {
        Map<String, String> args = new HashMap<>();
        args.put(FAKE_PHOTO_ARG, FAKE_PHOTO_ID);
        return args;
    }
}