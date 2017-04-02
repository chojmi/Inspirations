package com.github.chojmi.inspirations.presentation.gallery.grid;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.loadImage;

class GridAdapter extends BaseRecyclerViewAdapter<GridAdapter.GalleryViewHolder, Photo> {

    private Listener listener;

    GridAdapter(@Nullable Listener listener) {
        this.listener = listener;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.setPhoto(getItem(position));
    }

    interface Listener {
        void photoClicked(Photo photo);
    }

    class GalleryViewHolder extends BaseRecyclerViewAdapter.ViewHolder<Photo> {
        @BindView(R.id.photo) ImageView photoHolder;
        private Photo photo;

        GalleryViewHolder(View itemView) {
            super(itemView);
        }

        void setPhoto(Photo photo) {
            this.photo = photo;
            loadImage(photoHolder, photo.getUrl());
        }

        @OnClick(R.id.photo)
        void onPhotoClick(ImageView imageView) {
            if (listener == null) {
                return;
            }
            listener.photoClicked(photo);
        }
    }
}
