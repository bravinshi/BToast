package com.shi.btoast;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mLayout;

    private ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.rv_content);


        init();
    }

    private void init() {
        adapter = new ContentAdapter(MainActivity.this);

        List<DataBean> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            DataBean dataBean = new DataBean();
            dataBean.setName("DataBean: " + i);
            list.add(dataBean);
        }

        adapter.addData(list);
        mLayout.setLayoutManager(new LinearLayoutManager(
                MainActivity.this, LinearLayoutManager.VERTICAL, false));
        mLayout.setAdapter(adapter);
    }

    static class ContentAdapter extends SimpleRecyclerViewAdapter<com.shi.btoast.DataBean> {
        private Context context;
        private int[] colors = new int[]{Color.YELLOW, Color.BLUE, Color.CYAN};

        public ContentAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemType(com.shi.btoast.DataBean item) {
            return 0;
        }

        @Override
        public void onBindVH(RecyclerView.ViewHolder holder, int position, com.shi.btoast.DataBean item) {
            int colorPos = position % colors.length;
            holder.itemView.setBackgroundColor(colors[colorPos]);
            ((ContentViewHolder) holder).mText.setText(item.getName());
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.content_item, parent, false);
//            MutableLiveData
            return new ContentViewHolder(view);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView mText;
        private static int count = 0;

        public ContentViewHolder(View itemView) {
            super(itemView);
            count++;
            Log.d("VY", "count: " + count);
            mText = itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BToast.toast(v.getContext(), "这是一个测试用的Toast");
                }
            });
        }
    }
}
