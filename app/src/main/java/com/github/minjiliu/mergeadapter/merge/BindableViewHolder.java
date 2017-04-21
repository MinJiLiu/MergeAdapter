package com.github.minjiliu.mergeadapter.merge;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mj on 17/4/13.
 */

public class BindableViewHolder<T> extends RecyclerView.ViewHolder implements Bindable<T> {

    public BindableViewHolder(View itemView) {
        super(itemView);
    }

    public void onBindData(T t) {
        if (itemView instanceof Bindable) {
            //noinspection unchecked
            ((Bindable) itemView).onBindData(t);
        }
    }

}
