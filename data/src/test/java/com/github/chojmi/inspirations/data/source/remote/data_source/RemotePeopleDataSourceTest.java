package com.github.chojmi.inspirations.data.source.remote.data_source;

import android.os.Parcel;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder;
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
import retrofit2.http.QueryMap;

@RunWith(MockitoJUnitRunner.class)
public class RemotePeopleDataSourceTest {
    private static final String FAKE_USER_ID = "123";
    private static final String FAKE_USER_ARG = "fake_user_arg";

    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    private RemotePeopleDataSource remotePeopleDataSource;
    private TestObserver testObserver;
    private FakePersonEntityImpl fakePersonEntity;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() throws Exception {
        this.remotePeopleDataSource = new RemotePeopleDataSource(new FakePeopleService(), mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
        this.fakePersonEntity = new FakePersonEntityImpl();
    }

    @Test
    public void loadPersonInfoHappyCase() {
        Mockito.when(mockRemoteQueryProducer.produceLoadPersonInfoQuery(FAKE_USER_ID)).thenReturn(getFakeUserIdQueryMap());
        remotePeopleDataSource.loadPersonInfo(FAKE_USER_ID).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(fakePersonEntity);
        testObserver.assertComplete();
    }

    @Test
    public void loadUserPublicPhotosHappyCase() {
        Mockito.when(mockRemoteQueryProducer.produceLoadUserPublicPhotosQuery(FAKE_USER_ID, 1)).thenReturn(getFakeUserIdQueryMap());
        remotePeopleDataSource.loadUserPublicPhotos(FAKE_USER_ID).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(new ArrayList<>());
        testObserver.assertComplete();
    }

    private Map<String, String> getFakeUserIdQueryMap() {
        Map<String, String> args = new HashMap<>();
        args.put(FAKE_USER_ARG, FAKE_USER_ID);
        return args;
    }

    private static class FakeGalleryEntityImpl extends GalleryEntityImpl {

        @Override
        public int getPage() {
            return 0;
        }

        @Override
        public int getPages() {
            return 0;
        }

        @Override
        public int getPerpage() {
            return 0;
        }

        @Override
        public int getTotal() {
            return 0;
        }

        @Override
        public List getPhoto() {
            return new ArrayList();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

    private static class FakePersonEntityImpl extends PersonEntityImpl {

        @Override
        public String getId() {
            return null;
        }

        @Override
        public String getNsid() {
            return null;
        }

        @Override
        public int getIsPro() {
            return 0;
        }

        @Override
        public int getServer() {
            return 0;
        }

        @Override
        public int getFarm() {
            return 0;
        }

        @Override
        public ContentHolder getUsernameContentHolder() {
            return null;
        }

        @Override
        public ContentHolder getDescriptionContentHolder() {
            return null;
        }

        @Override
        public String getUsername() {
            return super.getUsername();
        }

        @Override
        public String getDescription() {
            return super.getDescription();
        }

        @Override
        public String getIconUrl() {
            return super.getIconUrl();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

    private class FakePeopleService implements PeopleService {
        @Override
        public Observable<PersonEntityImpl> loadPersonInfo(@QueryMap Map<String, String> options) {
            if (options.get(FAKE_USER_ARG).equals(FAKE_USER_ID)) {
                return Observable.just(fakePersonEntity);
            } else {
                return Observable.error(new Throwable("Wrong user id"));
            }
        }

        @Override
        public Observable<GalleryEntityImpl> loadUserPublicPhotos(@QueryMap Map<String, String> options) {
            if (options.get(FAKE_USER_ARG).equals(FAKE_USER_ID)) {
                return Observable.just(new FakeGalleryEntityImpl());
            } else {
                return Observable.error(new Throwable("Wrong user id"));
            }
        }
    }
}