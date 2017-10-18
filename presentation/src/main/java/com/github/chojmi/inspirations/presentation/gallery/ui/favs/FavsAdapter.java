package com.github.chojmi.inspirations.presentation.gallery.ui.favs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.blueprints.BaseRecyclerViewAdapter;

import butterknife.BindView;

public class FavsAdapter extends BaseRecyclerViewAdapter<FavsAdapter.ViewHolder, PersonEntity> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_favs_item, parent, false);
        return new ViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.renderView(getItem(position));
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder<PersonEntity> {
        @BindView(R.id.favs_item) FavsItemView favsItemView;

        ViewHolder(View itemView, ViewGroup parent) {
            super(itemView, parent);
        }

        public void renderView(PersonEntity personEntity) {
            favsItemView.renderView(personEntity);
        }
    }
}
