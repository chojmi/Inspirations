package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity
import com.google.gson.annotations.SerializedName

data class CommentEntityImpl(
        private val authorname: String,
        @SerializedName("_content") private val content: String) : CommentEntity {
    override fun getAuthorname(): String = authorname

    override fun getContent(): String = content
}