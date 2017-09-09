package com.github.chojmi.inspirations.data.entity

import com.github.chojmi.inspirations.domain.entity.GalleryEntity

data class GalleryEntityImpl(val page: Int,
                             val pages: Int,
                             val perpage: Int,
                             val total: Int,
                             private val photo: List<PhotoEntityImpl>) : GalleryEntity<PhotoEntityImpl> {
    override fun getPhoto(): List<PhotoEntityImpl> = photo
}