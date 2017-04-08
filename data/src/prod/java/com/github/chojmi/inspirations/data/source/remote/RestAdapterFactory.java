package com.github.chojmi.inspirations.data.source.remote;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
abstract class RestAdapterFactory implements TypeAdapterFactory {
    public static RestAdapterFactory create() {
        return new AutoValueGson_RestAdapterFactory();
    }
}
