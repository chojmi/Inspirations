package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.gallery.model.GridAdapterUiModel;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.item.GridItemBottomView;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.item.GridItemTopView;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class GridAdapter extends BaseRecyclerViewAdapter<GridAdapter.GalleryViewHolder, GridAdapterUiModel> {
    private final Listener listener;
    private final PublishSubject<Integer> profileClicksSubject = PublishSubject.create();
    private final PublishSubject<Integer> photoClicksSubject = PublishSubject.create();

    GridAdapter(Listener listener) {
        this.listener = listener;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_grid_item, parent, false);
        return new GalleryViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        GridAdapterUiModel uiModel = getItem(position);
        holder.setGridAdapterUiModel(uiModel);
        if (uiModel.getComments() == null && uiModel.getFavs() == null) {
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

    void setComments(int position, PhotoComments photoComments) {
        replace(position, GridAdapterUiModel.Companion.setComments(getItem(position), photoComments));
    }

    public Observable<PhotoWithAuthor> getProfileClicksObservable() {
        return profileClicksSubject.map(integer -> getItem(integer).getPhoto());
    }

    public Observable<PhotoWithAuthor> getPhotoClicksObservable() {
        return photoClicksSubject.map(integer -> getItem(integer).getPhoto());
    }

    interface Listener {
        void onNewItemBind(int position, PhotoWithAuthor photo);
    }

    class GalleryViewHolder extends BaseRecyclerViewAdapter.ViewHolder<PhotoWithAuthor> {
        @BindView(R.id.item_top) GridItemTopView gridItemTopView;
        @BindView(R.id.item_bottom) GridItemBottomView gridItemBottomView;

        GalleryViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
            gridItemTopView.getProfileClicksObservable()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(profileClicksSubject);

            gridItemTopView.getPhotoClicksObservable()
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(photoClicksSubject);
        }

        void setGridAdapterUiModel(GridAdapterUiModel gridAdapterUiModel) {
            gridItemTopView.setPhoto(gridAdapterUiModel.getPhoto());
            gridItemBottomView.setFavs(gridAdapterUiModel.getFavs());
            gridItemBottomView.setComments(gridAdapterUiModel.getComments());
        }

        void onViewRecycled() {
            gridItemTopView.onViewRecycled();
        }
    }
}
