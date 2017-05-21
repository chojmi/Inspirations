package com.github.chojmi.inspirations.data.source.repository;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.data.entity.photos.FakePhotoCommentsEntityImpl;
import com.github.chojmi.inspirations.data.entity.photos.FakePhotoFavsEntityImpl;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PhotosRepositoryTest {
    private static final String FAKE_PHOTO_ID = "123";
    private static final String FAKE_USERNAME = "fake_username";
    private static final String FAKE_ICON_URL = "fake_icon_url";
    private static final String FAKE_AUTHORNAME = "fake_authorname";
    private static final String FAKE_CONTENT = "fake_content";

    private PhotosDataSource photosDataSource;

    @Mock
    private PhotosDataSource mockLocalDataSource;
    @Mock
    private PhotosDataSource mockRemoteDataSource;

    @Before
    public void setUp() {
        photosDataSource = new PhotosRepository(mockRemoteDataSource, mockLocalDataSource);
    }

    @Test
    public void testLoadPhotoFavsFromRemoteHappyCase() {
        final PhotoFavsEntity favs = getFakePhotoFavs();
        given(mockLocalDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).willReturn(Observable.just(new FakePhotoFavsEntityImpl()));
        given(mockRemoteDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).willReturn(Observable.just(favs));

        photosDataSource.loadPhotoFavs(FAKE_PHOTO_ID).test().assertResult(favs);

        verify(mockLocalDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
        verify(mockRemoteDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
    }

    @Test
    public void testLoadPhotoFavsFromLocalHappyCase() {
        final PhotoFavsEntity favs = getFakePhotoFavs();
        given(mockLocalDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).willReturn(Observable.just(favs));
        given(mockRemoteDataSource.loadPhotoFavs(FAKE_PHOTO_ID)).willReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        photosDataSource.loadPhotoFavs(FAKE_PHOTO_ID).test().assertResult(favs);

        verify(mockLocalDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
        verify(mockRemoteDataSource, times(1)).loadPhotoFavs(FAKE_PHOTO_ID);
    }

    @Test
    public void testLoadPhotoCommentsFromRemoteHappyCase() {
        final PhotoCommentsEntity photoCommentsEntity = getFakePhotoCommentsEntity();
        given(mockLocalDataSource.loadPhotoComments(FAKE_PHOTO_ID)).willReturn(Observable.just(new FakePhotoCommentsEntityImpl()));
        given(mockRemoteDataSource.loadPhotoComments(FAKE_PHOTO_ID)).willReturn(Observable.just(photoCommentsEntity));

        photosDataSource.loadPhotoComments(FAKE_PHOTO_ID).test().assertResult(photoCommentsEntity);

        verify(mockLocalDataSource, times(1)).loadPhotoComments(FAKE_PHOTO_ID);
        verify(mockRemoteDataSource, times(1)).loadPhotoComments(FAKE_PHOTO_ID);
    }

    @Test
    public void testLoadPhotoCommentsFromLocalHappyCase() {
        final PhotoCommentsEntity photoCommentsEntity = getFakePhotoCommentsEntity();
        given(mockLocalDataSource.loadPhotoComments(FAKE_PHOTO_ID)).willReturn(Observable.just(photoCommentsEntity));
        given(mockRemoteDataSource.loadPhotoComments(FAKE_PHOTO_ID)).willReturn(Observable.create(e -> {
            throw new IllegalStateException("Shouldn't be invoked");
        }));

        photosDataSource.loadPhotoComments(FAKE_PHOTO_ID).test().assertResult(photoCommentsEntity);

        verify(mockLocalDataSource, times(1)).loadPhotoComments(FAKE_PHOTO_ID);
        verify(mockRemoteDataSource, times(1)).loadPhotoComments(FAKE_PHOTO_ID);
    }

    @NonNull
    private PhotoFavsEntity getFakePhotoFavs() {
        return new PhotoFavsEntity() {
            @Override
            public List<PersonEntity> getPeople() {
                return getFakeFavs();
            }

            @Override
            public int getPage() {
                return 0;
            }

            @Override
            public String getPhotoId() {
                return FAKE_PHOTO_ID;
            }

            @Override
            public int getTotal() {
                return 0;
            }
        };
    }

    @NonNull
    private List<PersonEntity> getFakeFavs() {
        List<PersonEntity> photos = new ArrayList<>();
        photos.add(new PersonEntity() {
            @Override
            public String getUsername() {
                return FAKE_USERNAME;
            }

            @Override
            public String getIconUrl() {
                return FAKE_ICON_URL;
            }
        });
        return photos;
    }

    @NonNull
    private PhotoCommentsEntity getFakePhotoCommentsEntity() {
        return new PhotoCommentsEntity() {

            @Override
            public List getComments() {
                return getFakeComments();
            }

            @Override
            public String getPhotoId() {
                return FAKE_PHOTO_ID;
            }
        };
    }

    @NonNull
    private List<CommentEntity> getFakeComments() {
        List<CommentEntity> fakeCommentsList = new ArrayList<>();
        fakeCommentsList.add(new CommentEntity() {
            @Override
            public String getAuthorname() {
                return FAKE_AUTHORNAME;
            }

            @Override
            public String getContent() {
                return FAKE_CONTENT;
            }
        });
        return fakeCommentsList;
    }
}