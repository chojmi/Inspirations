package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.presentation.data.BuildConfig;

import java.util.HashMap;
import java.util.Map;

public class RemoteQueryProducerImpl implements RemoteQueryProducer {

    @Override
    public Map<String, String> produceLoadGalleryQuery(String galleryId, int page) {
        Map<String, String> args = getBaseArgs("flickr.galleries.getPhotos");
        args.put("gallery_id", galleryId);
        args.put("page", String.valueOf(page));
        return args;
    }

    @Override
    public Map<String, String> produceLoadPersonInfoQuery(String userId) {
        Map<String, String> args = getBaseArgs("flickr.people.getInfo");
        args.put("user_id", userId);
        return args;
    }

    @Override
    public Map<String, String> produceLoadUserPublicPhotosQuery(String userId, int page) {
        Map<String, String> args = getBaseArgs("flickr.people.getPublicPhotos");
        args.put("user_id", userId);
        args.put("page", String.valueOf(page));
        return args;
    }

    @Override
    public Map<String, String> produceLoadPhotoFavsQuery(String photoId) {
        Map<String, String> args = getBaseArgs("flickr.photos.getFavorites");
        args.put("photo_id", photoId);
        return args;
    }

    @Override
    public Map<String, String> produceLoadPhotoComments(String photoId) {
        Map<String, String> args = getBaseArgs("flickr.photos.comments.getList");
        args.put("photo_id", photoId);
        return args;
    }

    private Map<String, String> getBaseArgs(String method) {
        Map<String, String> args = new HashMap<>();
        args.put("api_key", BuildConfig.API_KEY);
        args.put("format", "json");
        args.put("method", method);
        return args;
    }
}