package com.mini_proj.annetao.wego;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by huangweiping on 16/5/27.
 */
public class PhoneOrderDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public PhoneOrderDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        outRect.top = 0;
        outRect.bottom = 0;
        if (position == 0) {
            outRect.top = space / 2;
        } else if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = space / 2;
        }
    }

}