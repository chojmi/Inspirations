package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.photos.PersonEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.response.PhotoCommentsResponse;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.http.QueryMap;

@RunWith(MockitoJUnitRunner.class)
public class RemotePhotosDataSourceTest {
    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    private RemotePhotosDataSource remotePhotosDataSource;
    private TestObserver testObserver;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() throws Exception {
        this.remotePhotosDataSource = new RemotePhotosDataSource(new FakePhotosService(), mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }

    private class FakePhotosService implements PhotosService {
        @Override
        public Observable<List<PersonEntityImpl>> loadPhotoFavs(@QueryMap Map<String, String> options) {
            return null;
        }

        @Override
        public Observable<PhotoCommentsResponse> loadPhotoComments(@QueryMap Map<String, String> options) {
            return null;
        }
        //
//             if (options.get(FAKE_USER_ARG).equals(FAKE_USER_ID)) {
//            return Observable.just(new FakeGalleryEntityImpl());
//        } else {
//            return Observable.error(new Throwable("Wrong user id"));
//        }
    }
}