package com.github.chojmi.inspirations.data.source.remote.service;

import java.util.Map;

public interface RemoteQueryProducer {
    Map<String, String> produceLoadGalleryQuery(String galleryId, int page);

    Map<String, String> produceLoadPersonInfoQuery(String userId);

    Map<String, String> produceLoadUserPublicPhotosQuery(String userId, int page);

    Map<String, String> produceLoadPhotoFavsQuery(String photoId);

    Map<String, String> produceLoadPhotoComments(String photoId);
}
