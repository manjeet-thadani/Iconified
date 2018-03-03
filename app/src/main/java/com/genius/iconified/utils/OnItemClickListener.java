package com.genius.iconified.utils;

import android.view.View;

/**
 * Created by ADMIN on 10/10/2016.
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position, boolean isLongClick);

    void onItemLongClick(View view, int position, boolean isLongClick);
}
