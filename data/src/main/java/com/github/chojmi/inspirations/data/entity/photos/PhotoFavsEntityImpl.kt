package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity
import com.google.gson.annotations.SerializedName

data class PhotoFavsEntityImpl(private @SerializedName("person") val people: List<PersonEntityImpl>,
                               private val page: Int,
                               private @SerializedName("id") val photoId: String,
                               private val total: Int) : PhotoFavsEntity<PersonEntityImpl> {
    override fun getPeople(): List<PersonEntityImpl> = people

    override fun getPage(): Int = page

    override fun getPhotoId(): String = photoId

    override fun getTotal(): Int = total
}