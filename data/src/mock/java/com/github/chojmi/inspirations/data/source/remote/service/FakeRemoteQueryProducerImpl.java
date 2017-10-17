package com.github.chojmi.inspirations.data.source.remote.service;

import java.util.HashMap;
import java.util.Map;

public class FakeRemoteQueryProducerImpl implements RemoteQueryProducer {
    @Override
    public Map<String, String> produceLoadPersonInfoQuery(String userId) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadUserPublicPhotosQuery(String userId, int page) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadPhotoFavsQuery(String photoId) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadPhotoComments(String photoId) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadPhotoSizes(String photoId) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadPhotoInfo(String photoId) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadLoginData() {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceAddToFavs(String photoId) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceRemoveFromFavs(String photoId) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadGalleryByGalleryIdQuery(String galleryId, int page) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadGalleryByUserIdQuery(String userId, int page) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> produceLoadSearchPhoto(String text) {
        return new HashMap<>();
    }
}
