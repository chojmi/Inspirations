package com.github.chojmi.inspirations.data.entity.people

import com.github.chojmi.inspirations.data.entity.helpers.ContentHolder
import com.github.chojmi.inspirations.domain.entity.people.UserEntity

data class UserEntityImpl(private val id: String,
                          private val username: ContentHolder) : UserEntity {
    override fun getId(): String = id

    override fun getUsername(): String = username.content
}