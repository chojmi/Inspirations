package com.github.chojmi.inspirations.data.source.remote.data_source;

import android.annotation.SuppressLint;
import android.os.Parcel;

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
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.http.QueryMap;

@RunWith(MockitoJUnitRunner.class)
public class RemoteGalleriesDataSourceTest {
    private static final String FAKE_GALLERY_ID = "123";

    @Mock
    private RemoteQueryProducer mockRemoteQueryProducer;
    private RemoteGalleriesDataSource remoteGalleriesDataSource;
    private TestObserver testObserver;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() {
        this.remoteGalleriesDataSource = new RemoteGalleriesDataSource(new TestGalleriesService(), mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }

    @Test
    public void loadGalleryHappyCase() {
        Mockito.when(mockRemoteQueryProducer.produceLoadGalleryQuery(FAKE_GALLERY_ID, 1)).thenReturn(getFakeQueryMap());
        remoteGalleriesDataSource.loadGallery(FAKE_GALLERY_ID).subscribe(testObserver);
        testObserver.assertSubscribed();
        testObserver.assertResult(new ArrayList<>());
        testObserver.assertComplete();
    }

    private Map<String, String> getFakeQueryMap() {
        Map<String, String> args = new HashMap<>();
        args.put("gallery_id", FAKE_GALLERY_ID);
        return args;
    }

    private static class TestGalleriesService implements GalleriesService {
        @Override
        public Observable<GalleryEntityImpl> loadGallery(@QueryMap Map<String, String> options) {
            if (options.get("gallery_id").equals(FAKE_GALLERY_ID)) {
                return Observable.just(new FakeGalleryEntityImpl());
            } else {
                return Observable.error(new Throwable("Wrong gallery id"));
            }
        }
    }

    @SuppressLint("ParcelCreator")
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
        public List<PhotoEntityImpl> getPhoto() {
            return new ArrayList<>();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }
}