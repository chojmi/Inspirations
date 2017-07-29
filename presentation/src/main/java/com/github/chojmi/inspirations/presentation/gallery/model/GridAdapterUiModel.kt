package com.github.chojmi.inspirations.presentation.gallery.model

import io.reactivex.Observable
import timber.log.Timber
import java.util.*

data class GridAdapterUiModel(val photo: PhotoWithAuthor, val favs: PhotoFavs?, val comments: PhotoComments?) {

    companion object {
        fun create(photo: PhotoWithAuthor): GridAdapterUiModel {
            return GridAdapterUiModel(photo, null, null)
        }

        fun create(photos: List<PhotoWithAuthor>): List<GridAdapterUiModel> {
            val uiModels = ArrayList<GridAdapterUiModel>()
            Observable.fromIterable(photos)
                    .map({ photo -> GridAdapterUiModel.Companion.create(photo) })
                    .toList()
                    .subscribe({ list -> uiModels.addAll(list) }, { t -> Timber.d(t) })
            return uiModels
        }

        fun setFavs(model: GridAdapterUiModel, favs: PhotoFavs): GridAdapterUiModel {
            return GridAdapterUiModel(model.photo, favs, model.comments)
        }

        fun setComments(model: GridAdapterUiModel, comments: PhotoComments): GridAdapterUiModel {
            return GridAdapterUiModel(model.photo, model.favs, comments)
        }
    }
}
