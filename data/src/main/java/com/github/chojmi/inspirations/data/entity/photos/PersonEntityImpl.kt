package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity
import com.google.gson.annotations.SerializedName

data class PersonEntityImpl(private val username: String,
                            val nsid: String,
                            @SerializedName("iconserver") val server: Int,
                            @SerializedName("iconfarm") val farm: Int) : PersonEntity {
    override fun getUsername(): String {
        return username
    }

    override fun getIconUrl(): String {
        return ImageLinkProvider.provideLink(farm, server, nsid);
    }
}