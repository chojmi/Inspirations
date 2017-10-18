package com.github.chojmi.inspirations.presentation.blueprints;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, O> extends RecyclerView.Adapter<VH> {

    private static final PublishSubject<Integer> viewClickSubject = PublishSubject.create();
    private final List<O> dataset;

    public BaseRecyclerViewAdapter() {
        this.dataset = new ArrayList<>();
    }

    public void setData(List<O> dataset) {
        this.dataset.clear();
        addData(dataset);
    }

    public void addData(List<O> dataset) {
        if (dataset != null) {
            this.dataset.addAll(dataset);
        }
        notifyDataSetChanged();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, O data) {
        dataset.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(O data) {
        int position = dataset.indexOf(data);
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    // Replaces a RecyclerView item on a predefined position
    public void replace(int position, O data) {
        dataset.remove(position);
        dataset.add(position, data);
        notifyItemChanged(position);
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
        List<O> existChildList = dataset;
        checkAndMoveItems(existChildList, childList);
        checkAndChangeItems(existChildList, childList);
        checkAndRemoveItem(existChildList, childList);
        checkAndAddItem(existChildList, childList);
        checkAndMoveItems(existChildList, childList);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Nullable
    public O getItem(int position) {
        if (position < 0 && position >= dataset.size()) {
            return null;
        }
        return dataset.get(position);
    }

    public List<O> getItems() {
        return dataset;
    }

    public Observable<Integer> getOnItemClickPositionObservable() {
        return viewClickSubject;
    }

    public Observable<O> getOnItemClickObservable() {
        return getOnItemClickPositionObservable().map(this::getItem);
    }

    public static abstract class ViewHolder<O> extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView, ViewGroup parent) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            RxView.clicks(itemView)
                    .takeUntil(RxView.detaches(parent))
                    .map(o -> getAdapterPosition())
                    .subscribe(viewClickSubject);
        }
    }
}

