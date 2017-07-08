package com.github.chojmi.inspirations.presentation.gallery.model

import io.reactivex.Flowable
import timber.log.Timber
import java.util.*

data class GridAdapterUiModel(val photo: Photo, val favs: PhotoFavs?, val comments: PhotoComments?) {

    companion object {
        fun create(photo: Photo): GridAdapterUiModel {
            return GridAdapterUiModel(photo, null, null)
        }

        fun create(photos: List<Photo>): List<GridAdapterUiModel> {
            val uiModels = ArrayList<GridAdapterUiModel>()
            Flowable.fromIterable(photos)
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
