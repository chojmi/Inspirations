package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;

import java.util.HashMap;
import java.util.Map;

public class FakeRemoteQueryProducerImpl implements RemoteQueryProducer {
    @Override
    public Map<String, String> produceLoadGalleryQuery(String galleryId, int page) {
        return new HashMap<>();
    }

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
}
