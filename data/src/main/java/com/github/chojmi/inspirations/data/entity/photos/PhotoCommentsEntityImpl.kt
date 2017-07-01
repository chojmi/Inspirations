package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity
import com.google.gson.annotations.SerializedName

data class PhotoCommentsEntityImpl(private @SerializedName("comment") val comments: List<CommentEntityImpl>,
                                   private @SerializedName("photo_id") val photoId: String) : PhotoCommentsEntity<CommentEntityImpl> {
    override fun getComments(): List<CommentEntityImpl> {
        return comments
    }

    override fun getPhotoId(): String {
        return photoId
    }
}