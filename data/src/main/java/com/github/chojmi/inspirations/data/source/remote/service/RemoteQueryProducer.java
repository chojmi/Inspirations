package com.github.chojmi.inspirations.data.source.remote.service;

import java.util.Map;

public interface RemoteQueryProducer {
    Map<String, String> produceLoadGalleryByGalleryIdQuery(String galleryId, int page);

    Map<String, String> produceLoadGalleryByUserIdQuery(String userId, int page);

    Map<String, String> produceLoadPersonInfoQuery(String userId);

    Map<String, String> produceLoadUserPublicPhotosQuery(String userId, int page);

    Map<String, String> produceLoadPhotoFavsQuery(String photoId);

    Map<String, String> produceLoadPhotoComments(String photoId);

    Map<String, String> produceLoadPhotoSizes(String photoId);

    Map<String, String> produceLoadPhotoInfo(String photoId);

    Map<String, String> produceLoadSearchPhoto(String text);

    Map<String, String> produceLoadLoginData();
}
