package com.github.chojmi.inspirations.data.entity.photos

import com.github.chojmi.inspirations.data.entity.helpers.ImageLinkProvider
import com.github.chojmi.inspirations.domain.entity.photos.CommentEntity
import com.google.gson.annotations.SerializedName
import java.net.MalformedURLException
import java.net.URL

data class CommentEntityImpl(
        private val authorname: String,
        @SerializedName("_content") private val content: String,
        @SerializedName("iconserver") private val server: Int,
        @SerializedName("iconfarm") private val farm: Int,
        private val author: String) : CommentEntity {
    override fun getAuthorname(): String = authorname

    override fun getContent(): String = parseFlickrLinks(content)

    override fun getAuthorIcon(): String = ImageLinkProvider.provideLink(farm, server, author)

    override fun getAuthorId(): String = author

    private fun parseFlickrLinks(content: String): String {
        val result = StringBuilder()
        val parts = content.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (item in parts)
            try {
                if (item.length > 2) {
                    val url = URL(item.substring(1, item.length - 1))
                    result.append("<a href=\"").append(url).append("\">").append(url).append("</a> ")
                } else {
                    result.append(item).append(" ")
                }
            } catch (e: MalformedURLException) {
                result.append(item).append(" ")
            }

        return result.toString()
    }
}