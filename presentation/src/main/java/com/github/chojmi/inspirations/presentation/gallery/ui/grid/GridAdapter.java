package com.github.chojmi.inspirations.presentation.gallery.ui.grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.gallery.model.GridAdapterUiModel;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.item.GridItemBottomView;
import com.github.chojmi.inspirations.presentation.gallery.ui.grid.item.GridItemTopView;

import butterknife.BindView;

class GridAdapter extends BaseRecyclerViewAdapter<GridAdapter.GalleryViewHolder, GridAdapterUiModel> {
    private final Listener listener;

    GridAdapter(Listener listener) {
        this.listener = listener;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_grid, parent, false);
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
        replace(position, GridAdapterUiModel.setFavs(getItem(position), photoFavs));
    }

    void setComments(int position, PhotoComments photoComments) {
        replace(position, GridAdapterUiModel.setComments(getItem(position), photoComments));
    }

    interface Listener {
        void onNewItemBind(int position, Photo photo);
    }

    class GalleryViewHolder extends BaseRecyclerViewAdapter.ViewHolder<Photo> {
        @BindView(R.id.item_top) GridItemTopView gridItemTopView;
        @BindView(R.id.item_bottom) GridItemBottomView gridItemBottomView;

        GalleryViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
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
