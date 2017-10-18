package com.github.chojmi.inspirations.presentation.gallery.model

import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeEntity
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity
import io.reactivex.Observable
import timber.log.Timber
import java.util.*

data class GridAdapterUiModel(val photo: PhotoWithAuthor, val favs: PhotoFavs?, val photoInfo: PhotoInfoEntity?,
                              val photoSizes: PhotoSizeListEntity<PhotoSizeEntity>?) {

    companion object {
        fun create(photo: PhotoWithAuthor): GridAdapterUiModel =
                GridAdapterUiModel(photo, null, null, null)

        fun create(photos: List<PhotoWithAuthor>): List<GridAdapterUiModel> {
            val uiModels = ArrayList<GridAdapterUiModel>()
            Observable.fromIterable(photos)
                    .map({ photo -> GridAdapterUiModel.Companion.create(photo) })
                    .toList()
                    .subscribe({ list -> uiModels.addAll(list) }, { t -> Timber.d(t) })
            return uiModels
        }

        fun setFavs(model: GridAdapterUiModel, favs: PhotoFavs): GridAdapterUiModel =
                GridAdapterUiModel(model.photo, favs, model.photoInfo, model.photoSizes)

        fun setPhotoInfo(model: GridAdapterUiModel, photoInfo: PhotoInfoEntity): GridAdapterUiModel =
                GridAdapterUiModel(model.photo, model.favs, photoInfo, model.photoSizes)

        fun setPhotoSizes(model: GridAdapterUiModel, sizes: PhotoSizeListEntity<PhotoSizeEntity>) =
                GridAdapterUiModel(model.photo, model.favs, model.photoInfo, sizes)
    }
}
