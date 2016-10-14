package com.yff;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyScrollView.OnScrollListener{

    private LinearLayout activityMain;
    private MyScrollView myScrollView;
    private LinearLayoutForListView liearForList;
    private View topView;
    private View floatView;
    private Button btnTop;
    private Button btnFloat;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        assignViews();

        btnFloat.setOnClickListener(myClickListener);
        btnTop.setOnClickListener(myClickListener);
        myScrollView.setOnScrollListener(this);
        activityMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScroll(myScrollView.getScrollY());
            }
        });

        initData();


    }


    public void initData(){
        String [] listData=new String[30];
        for(int i=0;i<listData.length;i++){
            listData[i]=i+"位";
            Log.i("pos",listData[i]);
        }

//        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,listData);
//        liearForList.setAdapter(adapter);
//        liearForList.setOnItemClickListener(new LinearLayoutForListView.setOnItemClickListener() {
//            @Override
//            public void onItemClick(View v, Object obj, int position) {
//                Toast.makeText(MainActivity.this,position+"位",Toast.LENGTH_SHORT).show();
//            }
//        });

        MyAdapter myAdapter=new MyAdapter(this,listData);
        liearForList.setAdapter(myAdapter);


    }


    public class MyAdapter extends BaseAdapter{


        private String[] listData;
        private Context context;


        public MyAdapter(Context contexts,String[] listData) {
            this.context=contexts;
            this.listData = listData;
        }

        @Override
        public int getCount() {
            return listData.length;
        }

        @Override
        public Object getItem(int i) {
            return listData[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int pos, View view, ViewGroup viewGroup) {
             Holder holder;
            if(view==null){
                holder=new Holder();
                view= LayoutInflater.from(context).inflate(R.layout.list_item,null);
                holder.textView=(TextView)view.findViewById(R.id.text_item);
                holder.linearList=(LinearLayout)view.findViewById(R.id.linear_list);
                view.setTag(holder);
            }else {
                holder=(Holder)view.getTag();
            }
            holder.textView.setText(listData[pos]);
            holder.linearList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,listData[pos],Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }




    public class Holder{
        TextView textView;
        LinearLayout linearList;
    }


    public  void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public void onScroll(int scrollY) {
        int floatViewTop=Math.max(scrollY,topView.getTop());
        floatView.layout(0,floatViewTop,floatView.getWidth(),floatViewTop+floatView.getHeight());

    }


    public View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this,"悬浮窗按钮！",Toast.LENGTH_SHORT).show();
        }
    };

    private void assignViews() {
        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
        listView=(ListView)findViewById(R.id.listView);
        liearForList=(LinearLayoutForListView)findViewById(R.id.linearForList);
        topView = findViewById(R.id.top_view);
        floatView = findViewById(R.id.float_view);
        btnTop=(Button)topView.findViewById(R.id.top_btn);
        btnFloat=(Button)floatView.findViewById(R.id.top_btn);

    }
}
