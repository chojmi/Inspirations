package com.github.chojmi.inspirations.presentation.gallery.grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.model.gallery.Photo;

import butterknife.BindView;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.loadImage;

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

    class GalleryViewHolder extends BaseRecyclerViewAdapter.ViewHolder<Photo> {
        @BindView(R.id.photo) ImageView photoHolder;
        @BindView(R.id.person_icon) ImageView personIconHolder;
        @BindView(R.id.title) TextView titleTextView;
        @BindView(R.id.owner) TextView ownerTextView;
        private Photo photo;

        GalleryViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
        }

        void setPhoto(Photo photo) {
            this.photo = photo;
            loadImage(photoHolder, photo.getUrl());
            loadImage(personIconHolder, photo.getPerson().getIconUrl());
            titleTextView.setText(photo.getTitle());
            ownerTextView.setText(photo.getPerson().getUsername());
        }
    }
}
