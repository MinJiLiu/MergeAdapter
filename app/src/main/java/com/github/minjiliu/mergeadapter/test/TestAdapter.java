package com.github.minjiliu.mergeadapter.test;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mj on 17/4/19.
 */
public class TestAdapter extends RecyclerView.Adapter {

    private Context context;
    private int max;

    public TestAdapter(Context context, int max) {
        this.context = context;
        this.max = max;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 20) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.parseColor("#FF333333"));
            return new TextViewHolder(textView);
        }

        if (viewType == 21) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.BLUE);
            return new TextViewHolder(textView);
        }

        if (viewType == 22) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.RED);
            return new TextViewHolder(textView);
        }
        return new TextViewHolder(new TextView(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 20) {
            ((TextViewHolder)holder).setText("这是第" + position + "个条目，而且是深黑色条目");
            return;
        }

        if (getItemViewType(position) == 21) {
            ((TextViewHolder)holder).setText("这是第" + position + "个条目，而且是蓝色条目");
            return;
        }

        if (getItemViewType(position) == 22) {
            ((TextViewHolder)holder).setText("这是第" + position + "个条目，而且是红色条目");
            return;
        }

        if (holder instanceof TextViewHolder) {
            ((TextViewHolder)holder).setText("这是第" + position + "个条目");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            return 20;
        if (position % 3 == 0) {
            return 21;
        }
        if (position % 19 == 0) {
            return 22;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return max;
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {
        public TextViewHolder(View view) {
            super(view);
        }

        public void setText(String str) {
            ((TextView) itemView).setText(str);
        }
    }
}
