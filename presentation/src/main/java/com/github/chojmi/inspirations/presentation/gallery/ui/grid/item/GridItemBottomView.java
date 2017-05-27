package com.github.chojmi.inspirations.presentation.gallery.ui.grid.item;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.chojmi.inspirations.presentation.R;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoComments;
import com.github.chojmi.inspirations.presentation.gallery.model.PhotoFavs;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class GridItemBottomView extends LinearLayoutCompat {
    @BindView(R.id.comments_count) TextView commentsCount;
    @BindView(R.id.favs_count) TextView favsCount;

    public GridItemBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setComments(@Nullable PhotoComments photoComments) {
        if (photoComments == null) {
            commentsCount.setText("");
            return;
        }
        commentsCount.setText("Comments: " + photoComments.getComments().size());
    }

    public void setFavs(@Nullable PhotoFavs photoFavs) {
        if (photoFavs == null) {
            favsCount.setText("");
            return;
        }
        favsCount.setText("Favs: " + photoFavs.getTotal());

    }
}
