package com.github.chojmi.inspirations.presentation.blueprints;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, O> extends RecyclerView.Adapter<VH> {

    private final List<O> mDataset;

    public BaseRecyclerViewAdapter() {
        this.mDataset = new ArrayList<>();
    }

    public void setData(List<O> dataset) {
        mDataset.clear();
        if (dataset != null) {
            mDataset.addAll(dataset);
        }

        notifyDataSetChanged();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, O data) {
        mDataset.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(O data) {
        int position = mDataset.indexOf(data);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    protected void checkAndAddItem(@NonNull List<O> existChildList, @NonNull List<O> newChildList) {
        for (O newChild : newChildList) {
            if (!existChildList.contains(newChild)) {
                int newIndex = newChildList.indexOf(newChild);
                if (newIndex > existChildList.size()) {
                    newIndex = existChildList.size();
                }
                existChildList.add(newIndex, newChild);
                notifyItemInserted(newIndex);
            }
        }
    }

    protected void checkAndRemoveItem(@NonNull List<O> existChildList, @NonNull List<O> newChildList) {
        Iterator<O> i = existChildList.iterator();
        while (i.hasNext()) {
            O existChild = i.next();
            if (!newChildList.contains(existChild)) {
                int index = existChildList.indexOf(existChild);
                i.remove();
                notifyItemRemoved(index);
            }
        }
    }

    protected void checkAndMoveItems(@NonNull List<O> existChildList, @NonNull List<O> newChildList) {
        for (O newChild : newChildList) {
            int newIndex = newChildList.indexOf(newChild);
            int oldIndex = existChildList.indexOf(newChild);
            if (oldIndex == newIndex) {
                continue;
            }

            if (oldIndex == -1) {
                continue;
            }

            if (existChildList.size() <= newIndex) {
                continue;
            }

            int existIndex = existChildList.indexOf(newChild);
            existChildList.add(newIndex, existChildList.remove(existIndex));
            notifyItemMoved(oldIndex, newIndex);
        }
    }

    protected void checkAndChangeItems(@NonNull List<O> existChildList, @NonNull List<O> newChildList) {
        for (O newChild : newChildList) {
            int newIndex = newChildList.indexOf(newChild);
            if (existChildList.size() > newIndex) {
                O existChild = existChildList.get(newIndex);
                if (existChild.equals(newChild)) {
                    continue;
                }
                existChildList.set(newIndex, newChild);
                notifyItemChanged(newIndex);
            }
        }
    }

    protected void changeData(@NonNull List<O> childList) {
        List<O> existChildList = mDataset;
        checkAndMoveItems(existChildList, childList);
        checkAndChangeItems(existChildList, childList);
        checkAndRemoveItem(existChildList, childList);
        checkAndAddItem(existChildList, childList);
        checkAndMoveItems(existChildList, childList);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Nullable
    public O getItem(int position) {
        if (position < 0 && position >= mDataset.size()) {
            return null;
        }
        return mDataset.get(position);
    }

    public List<O> getItems() {
        return mDataset;
    }


    public interface OnItemClickListener<O> {
        void onItemClick(View view, int position);
    }

    public static abstract class ViewHolder<O> extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            this(itemView, null);
        }

        public ViewHolder(View itemView, final OnItemClickListener<O> onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (onItemClickListener == null) {
                return;
            }
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(view, getAdapterPosition()));
        }
    }
}
