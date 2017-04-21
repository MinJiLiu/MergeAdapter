package com.github.minjiliu.mergeadapter.merge;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj on 17/4/12.
 * todo
 */
public class SackOfViewsAdapter extends RecyclerView.Adapter {

    private List<View> views = null;
    // 包装View
    private List<RecyclerView.ViewHolder> holders = new ArrayList<>();

    public SackOfViewsAdapter(@NonNull List<View> views) {
        this.views = views;
        for (View view : this.views) {
            BindableViewHolder vh = new BindableViewHolder(view);
            holders.add(vh);
        }

        if (views.size() != holders.size()) {
            throw new IllegalStateException(String.format("views.size() = %d, holders.size() = %d"
                    , views.size(), holders.size()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0 || viewType >= holders.size()) {
            throw new IllegalStateException("ViewType计算错误");
        }

        return holders.get(viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 由外部自己实现自己的数据渲染
    }

    @Override
    public int getItemCount() {
        return views.size();
    }

    /**
     * 直接使用View的position来设置ViewType
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public boolean hasView(View view) {
        return views.contains(view);
    }
}
