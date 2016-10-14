package com.yff;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * yy   替代listView的linearLayout
 * Created by Administrator on 2016/10/14.
 */

public class LinearLayoutForListView extends LinearLayout {
    private BaseAdapter adapter;
    private setOnItemClickListener itemClickListener = null;

    /**
     * 绑定布局
     */
    public void bindLinearLayout() {
        if(adapter==null){
            return;
        }
        int count = adapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            final int tmp = i;
            final Object obj = adapter.getItem(i);
            View v = adapter.getView(i, null, null);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener!=null){
                        itemClickListener.onItemClick(view,obj,tmp);
                    }
                }
            });
            addView(v, i);
        }
        Log.i("countTAG", "" + count);
    }

    public LinearLayoutForListView(Context context) {
        super(context);
    }


    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnItemClickListener(setOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        //setAdapter时添加View
        bindLinearLayout();
    }

    public interface setOnItemClickListener{
        void onItemClick(View v, Object obj, int position);
    }
}
