package com.github.chojmi.inspirations.presentation.photo.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;
import com.github.chojmi.inspirations.presentation.gallery.model.Photo;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoWithAuthor;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.clearImageCache;
import static com.github.chojmi.inspirations.presentation.utils.ImageViewUtils.loadImage;

public class PhotoListAdapter extends BaseRecyclerViewAdapter<PhotoListAdapter.ViewHolder, Photo> {
    private final PublishSubject<Integer> photoClicksSubject = PublishSubject.create();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);
        return new ViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setPhoto(getItem(position));
    }

    @Override
    public boolean onFailedToRecycleView(PhotoListAdapter.ViewHolder holder) {
        holder.onViewRecycled();
        return super.onFailedToRecycleView(holder);
    }

    public Observable<Photo> getPhotoClicksSubject() {
        return photoClicksSubject.map(this::getItem);
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder<PhotoWithAuthor> {
        @BindView(R.id.photo) ImageView imageView;

        ViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
            RxView.clicks(imageView)
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(photoClicksSubject);
        }

        private void onViewRecycled() {
            clearImageCache(imageView);
        }

        public void setPhoto(Photo photo) {
            loadImage(imageView, photo.getUrl());
        }
    }
}
