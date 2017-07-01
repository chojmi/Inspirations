package com.github.chojmi.inspirations.data.entity.people

import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder
import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity
import com.google.gson.annotations.SerializedName

data class PersonEntityImpl(val id: String,
                            val nsid: String,
                            @SerializedName("ispro") val isPro: Int,
                            @SerializedName("iconserver") val server: Int,
                            @SerializedName("iconfarm") val farm: Int,
                            private val username: ContentHolder,
                            private val description: ContentHolder) : PersonEntity {
    fun getDescription(): String {
        return description.content
    }

    override fun getUsername(): String {
        return username.content
    }

    override fun getIconUrl(): String {
        return ImageLinkProvider.provideLink(farm, server, nsid)
    }
}