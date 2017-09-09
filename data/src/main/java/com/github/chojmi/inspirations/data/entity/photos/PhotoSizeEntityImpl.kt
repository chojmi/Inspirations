package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeEntity

data class PhotoSizeEntityImpl(private val width: Int, private val height: Int) : PhotoSizeEntity {
    override fun getWidth(): Int = width

    override fun getHeight(): Int = height
}