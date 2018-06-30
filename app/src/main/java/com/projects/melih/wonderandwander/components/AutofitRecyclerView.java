package com.projects.melih.wonderandwander.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.projects.melih.wonderandwander.R;

/**
 * Created by Melih Gültekin on 15.05.2018
 *
 * @see <a href="https://github.com/chiuki/android-recyclerview/blob/master/app/src/main/java/com/sqisland/android/recyclerview/AutofitRecyclerView.java">AutofitRecyclerView</a>
 */
public class AutofitRecyclerView extends RecyclerView {
    private static final int DEFAULT_SPAN_COUNT = 1;
    private int columnWidth;
    private final GridLayoutManager manager;

    public AutofitRecyclerView(Context context) {
        this(context, null);
    }

    public AutofitRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutofitRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutofitRecyclerView);
            columnWidth = array.getDimensionPixelSize(R.styleable.AutofitRecyclerView_itemWidth, -1);
            addItemDecoration(new SpaceItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.x_min_size)));
            array.recycle();
        }
        manager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        setLayoutManager(manager);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (columnWidth > 0) {
            //gets max of default span and calculated span count
            manager.setSpanCount(Math.max(DEFAULT_SPAN_COUNT, getMeasuredWidth() / columnWidth));
        }
    }

    private static class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int space;

        SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = 2 * space;
            outRect.top = 0;
        }
    }
}