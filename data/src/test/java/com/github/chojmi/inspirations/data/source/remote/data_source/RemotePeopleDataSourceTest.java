package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.FakePeopleService;
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
public class RemotePeopleDataSourceTest {
    private static final String FAKE_USER_ID = "123";
    private static final String FAKE_USER_ARG = "fake_user_arg";

    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    private RemotePeopleDataSource remotePeopleDataSource;
    private TestObserver testObserver;
    private FakePeopleService fakePeopleService;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() throws Exception {
        this.fakePeopleService = new FakePeopleService(getFakeUserIdQueryMap(), getFakeUserIdQueryMap());
        this.remotePeopleDataSource = new RemotePeopleDataSource(fakePeopleService, mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }

    @Test
    public void loadPersonInfoHappyCase() {
        Mockito.when(mockRemoteQueryProducer.produceLoadPersonInfoQuery(FAKE_USER_ID)).thenReturn(getFakeUserIdQueryMap());
        remotePeopleDataSource.loadPersonInfo(FAKE_USER_ID).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(fakePeopleService.getLoadPersonInfoResult());
        testObserver.assertComplete();
    }

    @Test
    public void loadUserPublicPhotosHappyCase() {
        Mockito.when(mockRemoteQueryProducer.produceLoadUserPublicPhotosQuery(FAKE_USER_ID, 1)).thenReturn(getFakeUserIdQueryMap());
        remotePeopleDataSource.loadUserPublicPhotos(FAKE_USER_ID).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(fakePeopleService.getLoadUserPublicPhotosResult().getPhoto());
        testObserver.assertComplete();
    }

    private Map<String, String> getFakeUserIdQueryMap() {
        Map<String, String> args = new HashMap<>();
        args.put(FAKE_USER_ARG, FAKE_USER_ID);
        return args;
    }
}