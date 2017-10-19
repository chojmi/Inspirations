package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider
import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity
import com.google.gson.annotations.SerializedName

data class CommentEntityImpl(
        private val authorname: String,
        @SerializedName("_content") private val content: String,
        @SerializedName("iconserver") private val server: Int,
        @SerializedName("iconfarm") private val farm: Int,
        private val author: String) : CommentEntity {
    override fun getAuthorname(): String = authorname

    override fun getContent(): String = content

    override fun getAuthorIcon(): String = ImageLinkProvider.provideLink(farm, server, author)

    override fun getAuthorId(): String = author
}