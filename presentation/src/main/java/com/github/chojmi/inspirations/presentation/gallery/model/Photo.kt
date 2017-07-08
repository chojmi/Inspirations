package com.github.chojmi.inspirations.presentation.gallery.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Photo(val id: String, val title: String, val url: String, val person: Person) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelPhoto.CREATOR
    }
}