package com.github.minjiliu.mergeadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.minjiliu.mergeadapter.merge.MergeAdapter;
import com.github.minjiliu.mergeadapter.test.TestAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MergeAdapter mergeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        mergeAdapter = new MergeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mergeAdapter);

        // 只添加一个View的测试
        testAddView(1);

        // 添加多个View
        testAddViewList(0, 100, true);

        // addView active
        testAddViewActive();

        // addAdapter
        testAddAdapter(50);

        // addAdapter,active
        testAddAdapterActive();

    }

    private void testAddAdapterActive() {
        TestAdapter testAdapter = new TestAdapter(this, 20);
        mergeAdapter.addAdapter(testAdapter, false);
        TestAdapter testAdapter1 = new TestAdapter(this, 1000);
        mergeAdapter.addAdapter(testAdapter1, true);
        mergeAdapter.setActive(testAdapter, true);
    }

    private void testAddAdapter(int max) {
        TestAdapter testAdapter = new TestAdapter(this, max);
        mergeAdapter.addAdapter(testAdapter);
    }

    private void testAddViewActive() {
        testAddViewList(100, 200, true);
        testAddViewList(0, 100, false);
    }

    private void testAddViewList(int min, int max, boolean active) {
        ArrayList<View> views = new ArrayList<>();
        for (int i = min; i < max; i++) {
            views.add(getTextView(i));
        }

        mergeAdapter.addView(views, active);
    }

    private void testAddView(int i) {
        TextView view = getTextView(i);
        mergeAdapter.addView(view);
        mergeAdapter.notifyDataSetChanged();
        mergeAdapter.setActive(view, false);
        mergeAdapter.setActive(view, true);
    }

    @NonNull
    private TextView getTextView(int i) {
        TextView view = new TextView(this);
        view.setTextColor(this.getResources().getColor(android.R.color.white));
        view.setPadding(10,10,10,10);
        view.setText("测试" + i);
        view.setBackgroundResource(android.R.color.darker_gray);
        return view;
    }
}
