package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.PhotoEntityImpl;
import com.github.chojmi.inspirations.data.entity.people.PersonEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.PeopleService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class RemotePeopleDataSourceTest {
    private static final String FAKE_USER_ID = "123";
    private final Map<String, String> fakeQueryMap = new HashMap<>();
    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    @Mock private PeopleService mockPeopleService;
    private TestObserver testObserver;
    private RemotePeopleDataSource remotePeopleDataSource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() throws Exception {
        this.remotePeopleDataSource = new RemotePeopleDataSource(mockPeopleService, mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }

    @Test
    public void loadPersonInfoHappyCase() {
        final PersonEntityImpl mockPhotoEntityImpl = Mockito.mock(PersonEntityImpl.class);
        Mockito.when(mockRemoteQueryProducer.produceLoadPersonInfoQuery(FAKE_USER_ID)).thenReturn(fakeQueryMap);
        Mockito.when(mockPeopleService.loadPersonInfo(fakeQueryMap)).thenReturn(Observable.just(mockPhotoEntityImpl));

        remotePeopleDataSource.loadPersonInfo(FAKE_USER_ID).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(mockPhotoEntityImpl);
        testObserver.assertComplete();
    }

    @Test
    public void loadUserPublicPhotosHappyCase() {
        final List<PhotoEntityImpl> result = new ArrayList<>();
        final GalleryEntityImpl mockGalleryEntityImpl = Mockito.mock(GalleryEntityImpl.class);
        Mockito.when(mockRemoteQueryProducer.produceLoadUserPublicPhotosQuery(FAKE_USER_ID, 1)).thenReturn(fakeQueryMap);
        Mockito.when(mockPeopleService.loadUserPublicPhotos(fakeQueryMap)).thenReturn(Observable.just(mockGalleryEntityImpl));
        Mockito.when(mockGalleryEntityImpl.getPhoto()).thenReturn(result);

        remotePeopleDataSource.loadUserPublicPhotos(FAKE_USER_ID).subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(result);
        testObserver.assertComplete();
    }
}