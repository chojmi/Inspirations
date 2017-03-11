package com.github.chojmi.inspirations.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chojmi.inspirations.R;
import com.github.chojmi.inspirations.data.model.Photo;
import com.github.chojmi.inspirations.ui.blueprints.BaseRecyclerViewAdapter;

import butterknife.BindView;

import static com.github.chojmi.inspirations.utils.ImageViewUtils.loadImage;

public class GalleryAdapter extends BaseRecyclerViewAdapter<GalleryAdapter.GalleryViewHolder, Photo> {

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.setPhoto(getItem(position));
    }

    class GalleryViewHolder extends BaseRecyclerViewAdapter.ViewHolder<Photo> {
        @BindView(R.id.photo)
        ImageView photoHolder;

        public GalleryViewHolder(View itemView) {
            super(itemView);
        }

        void setPhoto(Photo photo) {
            loadImage(photoHolder, photo.getUrl());
        }
    }
}
