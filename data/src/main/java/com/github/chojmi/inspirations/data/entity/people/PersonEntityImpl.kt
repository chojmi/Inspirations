package com.github.chojmi.inspirations.data.entity.people

import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder
import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity
import com.google.gson.annotations.SerializedName

data class PersonEntityImpl(private val id: String,
                            val nsid: String,
                            @SerializedName("ispro") val isPro: Int,
                            @SerializedName("iconserver") val server: Int,
                            @SerializedName("iconfarm") val farm: Int,
                            private val username: ContentHolder,
                            private val description: ContentHolder) : PersonEntity {
    fun getDescription(): String = description.content

    override fun getId(): String = id

    override fun getUsername(): String = username.content

    override fun getIconUrl(): String = ImageLinkProvider.provideLink(farm, server, nsid)
}