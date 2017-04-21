package com.github.minjiliu.mergeadapter.merge;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj on 17/4/12.
 * 封装子adaper的类
 */
public class PieceStateRoster {

    // 一个用来存放所有的adapter的列表，必须是ArrayList，不然View的顺序会乱掉
    private ArrayList<PieceState> pieces = new ArrayList<>();

    // 一个用来放激活的adapter的列表
    private ArrayList<RecyclerView.Adapter> actives = new ArrayList<>();

    /**
     * 添加子adapter，被添加进入的子adapter默认处于激活状态
     * @param adapter 被添加的adapter
     */
    void add(RecyclerView.Adapter adapter) {
        add(adapter, true);
    }

    /**
     * 添加子adapter
     * @param adapter 被添加的adapter
     * @param isActive true，激活，将会显示；false，关闭，不会显示
     */
    void add(RecyclerView.Adapter adapter, boolean isActive) {
        int size = pieces.size();
        pieces.add(size, new PieceState(adapter, size, isActive));
        actives.clear();
    }

    void setActive(RecyclerView.Adapter adapter, boolean isActive) {
        for (PieceState piece : pieces) {
            if (piece.adapter == adapter) {
                piece.isActive = isActive;
                actives.clear();
                break;
            }
        }
    }

    void setActive(View view, boolean isActive) {
        for (PieceState piece : pieces) {
            if (piece.adapter instanceof SackOfViewsAdapter && ((SackOfViewsAdapter) piece
                    .adapter).hasView(view)) {
                piece.isActive = isActive;
                actives.clear();
                break;
            }
        }
    }

    /**
     * @return 返回被激活的adapter列表
     */
    @NonNull
    public List<RecyclerView.Adapter> getPieces() {
        if (actives.size() == 0) {
            // 遍历所有的adapter，将active的adapter add进actives
            for (PieceState piece : pieces) {
                if (piece.isActive) {
                    actives.add(piece.adapter);
                }
            }
        }

        return actives;
    }

    /**
     * @return 返回所有被添加进入的Adapter，不论是否激活
     */
    public List<PieceState> getRawPieces() {
        return pieces;
    }
}
