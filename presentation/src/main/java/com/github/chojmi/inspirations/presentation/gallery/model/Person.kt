package com.github.chojmi.inspirations.presentation.gallery.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Person(val username: String, val iconUrl: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelPerson.CREATOR
    }
}