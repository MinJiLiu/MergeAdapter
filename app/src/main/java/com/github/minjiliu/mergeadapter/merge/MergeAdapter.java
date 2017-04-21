package com.github.minjiliu.mergeadapter.merge;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj on 17/4/12.
 */

public class MergeAdapter extends RecyclerView.Adapter {
    // 为每个子类设置的ViewType偏移量
    public static final int INT_OFFSET = 10000;
    // 需要一个列表来封装子adapter
    PieceStateRoster pieces = new PieceStateRoster();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 确认ViewType属于哪个Piece中的
        int index = viewType / INT_OFFSET;
        // 计算真实的type
        int type = viewType % INT_OFFSET;
        RecyclerView.Adapter adapter = pieces.getPieces().get(index);
        return adapter.onCreateViewHolder(parent, type);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        for (RecyclerView.Adapter adapter : getPieces()) {
            int size = adapter.getItemCount();
            if (position < size) {
                //noinspection unchecked
                adapter.onBindViewHolder(holder, position);
                break;
            }
            position -= size;
        }
    }

    /**
     * 遍历获取所有激活的子adapter中的条目数量
     */
    @Override
    public int getItemCount() {
        int count = 0;
        for (RecyclerView.Adapter adapter : getPieces()) {
            count += adapter.getItemCount();
        }
        return count;
    }

    /**
     * 为每个adapter重新分配各自的ViewType
     * 通过偏移量offset来实现，在所有的子adapter自己设置的ViewType之前设置一个offset来实现，
     * 这个offset是10000*adapter在所有adapter中的位置，所以每一个原始的ViewType都会得到一个
     * 与其自身所在的adapter的位置position相关的前缀
     */
    @Override
    public int getItemViewType(int position) {
        // 获取对应的adapter自己的ViewType
        PieceState pieceState;
        int type = 0;
        int offset = 0;
        for (RecyclerView.Adapter adapter : pieces.getPieces()) {
            // 该position属于当前piece
            int size = adapter.getItemCount();
            if (position < size) {
                type = adapter.getItemViewType(position);
                break;
            }
            position -= size;
            offset += INT_OFFSET;
        }

        // 对type进行一次校验，防止type大于10000造成错误
        if (type >= INT_OFFSET) {
            throw new IllegalArgumentException("子Adatper的ViewType必须 < 10000");
        }
        return type + offset;
    }

    /**
     * @return 返回所有激活的adapter
     */
    protected List<RecyclerView.Adapter> getPieces() {
        return pieces.getPieces();
    }

    /**
     * 添加Adapter，默认激活
     * @param adapter 添加的adapter
     */
    public void addAdapter(RecyclerView.Adapter adapter) {
        pieces.add(adapter);
    }

    /**
     * 向MergeAdapter中添加adapter
     * @param adapter 被添加的adapter
     * @param enabled true,激活显示;false,不显示
     */
    public void addAdapter(RecyclerView.Adapter adapter, boolean enabled) {
        pieces.add(adapter, enabled);
        notifyDataSetChanged();
    }

    /**
     * 直接往adapter中添加一个View，被添加的View默认处于激活的状态
     * @param view 被添加的View
     */
    public void addView(View view) {
        addView(view, true);
    }

    /**
     * 直接往adapter中添加一个View
     * @param view 被添加的View
     * @param enabled 被添加的View是否被激活
     */
    public void addView(View view, boolean enabled) {
        ArrayList<View> views = new ArrayList<>();
        views.add(view);
        addView(views, enabled);
    }

    /**
     * 往adapter中添加一系列的View
     * @param views 被添加的View列表
     * @param enabled 被添加的View是否激活
     */
    public void addView(List<View> views, boolean enabled) {
        addAdapter(new SackOfViewsAdapter(views), enabled);
    }

    public void setActive(RecyclerView.Adapter adapter, boolean isActive) {
        pieces.setActive(adapter, isActive);
        notifyDataSetChanged();
    }

    /**
     * 设置指定View的激活状态
     * @param view View
     * @param isActive true，激活；false，关闭
     */
    public void setActive(View view, boolean isActive) {
        pieces.setActive(view, isActive);
        notifyDataSetChanged();
    }
}
