package com.github.minjiliu.mergeadapter.merge;

import android.support.v7.widget.RecyclerView;

/**
 * Created by mj on 17/4/12.
 * 封装每一个子adapter
 */
public class PieceState {

    RecyclerView.Adapter adapter;
    // 记录是否激活，添加后默认激活
    boolean isActive = true;
    int position = -1; // 在父adapter中的位置

    public PieceState(RecyclerView.Adapter adapter, int position, boolean active) {
        this.adapter = adapter;
        isActive = active;
        this.position = position;
    }

    /**
     * @return 返回本Piece在父Adapter中的位置
     */
    public int getPosition() {
        if (position == -1) {
            throw new IllegalStateException("该Piece在父类中的位置异常，是否是手动创建的PieceState对象?");
        }
        return position;
    }
}
