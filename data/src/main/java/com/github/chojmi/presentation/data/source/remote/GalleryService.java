package com.github.chojmi.presentation.data.source.remote;

import com.github.chojmi.presentation.data.model.gallery.Gallery;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GalleryService {

    @GET("services/rest")
    Observable<Gallery> loadGallery(@QueryMap Map<String, String> options);
}
