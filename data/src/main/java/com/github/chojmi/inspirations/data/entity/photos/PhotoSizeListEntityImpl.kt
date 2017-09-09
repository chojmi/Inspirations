package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeEntity
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity
import com.google.gson.annotations.SerializedName

data class PhotoSizeListEntityImpl(private @SerializedName("size") val sizes: List<PhotoSizeEntityImpl>) : PhotoSizeListEntity<PhotoSizeEntity> {
    override fun getSizes(): List<PhotoSizeEntity> = sizes

    override fun getRatio(): Float = getSizes().last().height.toFloat() / getSizes().last().width
}