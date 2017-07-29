package com.github.chojmi.inspirations.presentation.gallery.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class PhotoWithAuthor(val photo: Photo, val person: Person) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelPhoto.CREATOR
    }
}