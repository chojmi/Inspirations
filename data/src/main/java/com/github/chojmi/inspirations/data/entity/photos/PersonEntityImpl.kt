package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity
import com.google.gson.annotations.SerializedName

data class PersonEntityImpl(private val username: String,
                            private val nsid: String,
                            @SerializedName("iconserver") private val server: Int,
                            @SerializedName("iconfarm") private val farm: Int) : PersonEntity {

    override fun getId(): String = nsid

    override fun getUsername(): String = username

    override fun getIconUrl(): String = ImageLinkProvider.provideLink(farm, server, nsid)
}