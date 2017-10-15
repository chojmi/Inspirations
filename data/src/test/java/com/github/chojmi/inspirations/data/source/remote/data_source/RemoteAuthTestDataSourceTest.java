package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.entity.auth.LoginDataEntityImpl;
import com.github.chojmi.inspirations.data.entity.people.PersonEntityImpl;
import com.github.chojmi.inspirations.data.source.remote.service.AuthTestService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.utils.TestAndroidScheduler;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteAuthTestDataSourceTest {
    private final Map<String, String> fakeQueryMap = new HashMap<>();
    @Mock private RemoteQueryProducer mockRemoteQueryProducer;
    @Mock private AuthTestService mockAuthTestService;
    private TestObserver<PersonEntity> testObserver;

    private RemoteAuthTestDataSource remoteAuthTestDataSource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        new TestAndroidScheduler().init();
    }

    @Before
    public void setUp() {
        this.remoteAuthTestDataSource = new RemoteAuthTestDataSource(mockAuthTestService, mockRemoteQueryProducer);
        this.testObserver = new TestObserver();
    }

    @Test
    public void loadLoginDataHappyCase() {
        PersonEntityImpl result = Mockito.mock(PersonEntityImpl.class);
        LoginDataEntityImpl loginDataEntity = Mockito.mock(LoginDataEntityImpl.class);
        when(mockRemoteQueryProducer.produceLoadLoginData()).thenReturn(fakeQueryMap);
        when(mockAuthTestService.loadLoginData(fakeQueryMap)).thenReturn(Observable.just(loginDataEntity));
        when(loginDataEntity.getUser()).thenReturn(result);

        remoteAuthTestDataSource.getLoginData().subscribe(testObserver);

        testObserver.assertSubscribed();
        testObserver.assertResult(result);
        testObserver.assertComplete();
    }
}