package com.github.chojmi.inspirations.presentation.gallery.grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.gallery.grid.item.GalleryItemTopView;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import butterknife.BindView;

class GridAdapter extends BaseRecyclerViewAdapter<GridAdapter.GalleryViewHolder, Photo> {

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_grid, parent, false);
        return new GalleryViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.setPhoto(getItem(position));
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

    class GalleryViewHolder extends BaseRecyclerViewAdapter.ViewHolder<Photo> {
        @BindView(R.id.item_top) GalleryItemTopView galleryItemTopView;

        GalleryViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
        }

        void setPhoto(Photo photo) {
            galleryItemTopView.setPhoto(photo);
        }

        void onViewRecycled() {
            galleryItemTopView.onViewRecycled();
        }
    }
}
