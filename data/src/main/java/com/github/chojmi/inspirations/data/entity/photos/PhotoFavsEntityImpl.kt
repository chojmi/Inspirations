package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity
import com.google.gson.annotations.SerializedName

data class PhotoFavsEntityImpl(private @SerializedName("person") val people: List<PersonEntityImpl>,
                               private val page: Int,
                               private @SerializedName("id") val photoId: String,
                               private val total: Int) : PhotoFavsEntity<PersonEntityImpl> {
    override fun getPeople(): List<PersonEntityImpl> {
        return people
    }

    override fun getPage(): Int {
        return page
    }

    override fun getPhotoId(): String {
        return photoId
    }

    override fun getTotal(): Int {
        return total
    }
}