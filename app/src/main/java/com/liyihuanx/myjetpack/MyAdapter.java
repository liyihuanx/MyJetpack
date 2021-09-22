package com.liyihuanx.myjetpack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson_Ho on 17/4/26.
 */
public class MyAdapter extends DelegateAdapter.Adapter<MyAdapter.MainViewHolder> {
    // 使用DelegateAdapter首先就是要自定义一个它的内部类Adapter，让LayoutHelper和需要绑定的数据传进去
    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法，其他的都是一样的做法.

    private ArrayList<HashMap<String, Object>> listItem;
    // 用于存放数据列表

    private Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count = 0;

    // 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public MyAdapter(Context context, LayoutHelper layoutHelper, int count, ArrayList<HashMap<String, Object>> listItem) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300), listItem);
    }

    public MyAdapter(Context context, LayoutHelper layoutHelper, int count, RecyclerView.LayoutParams layoutParams, ArrayList<HashMap<String, Object>> listItem) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.listItem = listItem;


    }

    // 把ViewHolder绑定Item的布局
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }


    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }


    // 绑定Item的数据
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.Text.setText((String) listItem.get(position).get("ItemTitle"));
        holder.image.setImageResource((Integer) listItem.get(position).get("ItemImage"));


    }

    // 返回Item数目
    @Override
    public int getItemCount() {
        return count;
    }


    //定义Viewholder
    class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView Text;
        public ImageView image;

        public MainViewHolder(View root) {
            super(root);

            // 绑定视图
            Text = (TextView) root.findViewById(R.id.Item);
            image = (ImageView) root.findViewById(R.id.Image);

        }

        public TextView getText() {
            return Text;
        }


    }
}