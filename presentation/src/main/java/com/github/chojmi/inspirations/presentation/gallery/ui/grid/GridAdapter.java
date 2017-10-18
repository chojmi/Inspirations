package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.common.PhotoDetailsView;
import com.github.chojmi.inspirations.presentation.gallery.model.GridAdapterUiModel;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

class GridAdapter extends BaseRecyclerViewAdapter<GridAdapter.GalleryViewHolder, GridAdapterUiModel> {
    private final Listener listener;
    private final PublishSubject<Integer> profileClicksSubject = PublishSubject.create();
    private final PublishSubject<Pair<Integer, ImageView>> photoClicksSubject = PublishSubject.create();
    private final PublishSubject<Integer> favsClicksSubject = PublishSubject.create();
    private final PublishSubject<Integer> favsIconClicksSubject = PublishSubject.create();
    private final PublishSubject<Integer> commentsClicksSubject = PublishSubject.create();
    private final PublishSubject<Integer> commentsIconClicksSubject = PublishSubject.create();

    GridAdapter(@NonNull Listener listener) {
        this.listener = Preconditions.checkNotNull(listener);
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_grid_item, parent, false);
        return new GalleryViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        GridAdapterUiModel uiModel = getItem(position);
        holder.renderView(uiModel);
        if (uiModel.getPhotoInfo() == null && uiModel.getFavs() == null) {
            listener.onNewItemBind(position, uiModel.getPhoto());
        }
    }

    @Override
    public void onViewRecycled(GalleryViewHolder holder) {
        holder.onViewRecycled();
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(GalleryViewHolder holder) {
        holder.onViewRecycled();
        return super.onFailedToRecycleView(holder);
    }

    void setFavs(int position, PhotoFavs photoFavs) {
        replace(position, GridAdapterUiModel.Companion.setFavs(getItem(position), photoFavs));
    }

    void showPhotoInfo(int position, PhotoInfoEntity photoInfo) {
        replace(position, GridAdapterUiModel.Companion.setPhotoInfo(getItem(position), photoInfo));
    }

    void setPhotoSizes(int position, PhotoSizeListEntity sizeList) {
        replace(position, GridAdapterUiModel.Companion.setPhotoSizes(getItem(position), sizeList));
    }

    Observable<PhotoWithAuthor> getProfileClicksObservable() {
        return profileClicksSubject.map(position -> getItem(position).getPhoto());
    }

    Observable<Pair<Integer, ImageView>> getPhotoClicksObservable() {
        return photoClicksSubject;
    }

    Observable<PhotoWithAuthor> getCommentsClicksObservable() {
        return commentsClicksSubject.map(position -> getItem(position).getPhoto());
    }

    Observable<PhotoWithAuthor> getCommentsIconClicksObservable() {
        return commentsIconClicksSubject.map(position -> getItem(position).getPhoto());
    }

    Observable<PhotoWithAuthor> getFavsClicksObservable() {
        return favsClicksSubject.map(position -> getItem(position).getPhoto());
    }

    Observable<Integer> getFavsIconClicksObservable() {
        return favsIconClicksSubject;
    }

    interface Listener {
        void onNewItemBind(int position, PhotoWithAuthor photo);
    }

    class GalleryViewHolder extends BaseRecyclerViewAdapter.ViewHolder<PhotoWithAuthor> {
        @BindView(R.id.item_top) GridItemTopView gridItemTopView;
        @BindView(R.id.item_bottom) PhotoDetailsView photoDetailsView;

        GalleryViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
            gridItemTopView.getProfileClicksObservable()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(profileClicksSubject);

            gridItemTopView.getPhotoClicksObservable()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> new Pair<>(getAdapterPosition(), (ImageView) gridItemTopView.findViewById(R.id.photo)))
                    .subscribe(photoClicksSubject);

            photoDetailsView.getCommentsRootClicks()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(commentsClicksSubject);

            photoDetailsView.getCommentsIconClicks()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(commentsIconClicksSubject);

            photoDetailsView.getFavsIconClicks()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(favsIconClicksSubject);

            photoDetailsView.getFavsRootClicks()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(favsClicksSubject);
        }

        void renderView(GridAdapterUiModel gridAdapterUiModel) {
            gridItemTopView.setPhoto(gridAdapterUiModel.getPhoto(), gridAdapterUiModel.getPhotoSizes());
            photoDetailsView.setFavs(gridAdapterUiModel.getFavs());
            photoDetailsView.setPhotoInfo(gridAdapterUiModel.getPhotoInfo());
        }

        void onViewRecycled() {
            gridItemTopView.onViewRecycled();
        }
    }
}