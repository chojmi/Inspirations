package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder
import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity
import com.google.gson.annotations.SerializedName

class PhotoInfoEntityImpl(
        private val id: String,
        private val owner: PersonEntityImpl,
        private val title: ContentHolder,
        @SerializedName("comments") private val commentsCount: ContentHolder,
        private val isfavorite: Int) : PhotoInfoEntity {

    override fun isFav(): Boolean {
        return isfavorite == 1
    }

    override fun getCommentsCount(): Int = Integer.valueOf(commentsCount.content)

    override fun getId(): String = id

    override fun getTitle(): String = title.content

    override fun getUrl(): String = owner.iconUrl

    override fun getOwnerId(): String = owner.id
}
