package com.github.chojmi.inspirations.data.entity

import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider
import com.github.chojmi.inspirations.domain.entity.PhotoEntity
import com.google.gson.annotations.SerializedName

data class PhotoEntityImpl(
        private val id: String,
        private val owner: String,
        private val secret: String,
        private val server: Int,
        private val farm: Int,
        private val title: String,
        @SerializedName("ispublic") val isPublic: Int,
        @SerializedName("isfriend") val isFriend: Int,
        @SerializedName("isfamily") val isFamily: Int,
        @SerializedName("is_primary") val isPrimary: Int,
        @SerializedName("has_comment") val hasComment: Int) : PhotoEntity {

    override fun getId(): String = id

    override fun getTitle(): String = title

    override fun getUrl(): String = ImageLinkProvider.provideLink(farm, server, id, secret)

    override fun getOwnerId(): String = owner
}