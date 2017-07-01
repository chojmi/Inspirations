package com.github.chojmi.inspirations.data.entity

import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider
import com.github.chojmi.inspirations.domain.entity.PhotoEntity
import com.google.gson.annotations.SerializedName;

data class PhotoEntityImpl(
        private val id: String,
        val owner: String,
        val secret: String,
        val server: Int,
        val farm: Int,
        private val title: String,
        @SerializedName("ispublic") val isPublic: Int,
        @SerializedName("isfriend") val isFriend: Int,
        @SerializedName("isfamily") val isFamily: Int,
        @SerializedName("is_primary") val isPrimary: Int,
        @SerializedName("has_comment") val hasComment: Int) : PhotoEntity {
    override fun getId(): String {
        return id
    }

    override fun getTitle(): String {
        return title
    }

    override fun getUrl(): String {
        return ImageLinkProvider.provideLink(farm, server, id, secret)
    }
}