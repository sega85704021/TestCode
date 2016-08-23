package com.testcode.downselect;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private EditText et_input;
    private ArrayList<String> datas;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ib_dropdown).setOnClickListener(this);
        et_input =(EditText) findViewById(R.id.et_input);
    }

    @Override
    public void onClick(View view) {
        showPopwindow();
    }

    private void showPopwindow() {
        initListView();
        //显示下拉框
        popupWindow = new PopupWindow(listView, et_input.getWidth(), 300);
        //显示在指定控件下
        //设置点击外部区域,popup可隐藏
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//设置空的背景,用来响应点击事件
        popupWindow.showAsDropDown(et_input,10,0);
        popupWindow.setFocusable(true);//设置可获取焦点

    }

    //初始化要显示的内容
    private void initListView() {
        listView = new ListView(this);
        //去掉默认分割线
        listView.setDividerHeight(0);
        listView.setBackgroundResource(R.mipmap.listview_background);
        listView.setOnItemClickListener(this);
         datas = new ArrayList<String>();
        //创建一些数据
        for (int i = 0; i< 30; i++) {
            datas.add((10000 + i ) + "");
        }
        listView.setAdapter(new MyAdapter());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView,
                            View view, int position, long l) {
        String string = datas.get(position);
        et_input.setText(string);
        popupWindow.dismiss();
         
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datas.size() ;
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View converView, ViewGroup parent) {
            View view;
            if(converView == null) {
                view = View.inflate(parent.getContext(),R.layout.item_number,null);
            }else {
                view = converView;
            }
            TextView tv_number = (TextView) view.findViewById(R.id.tv_nuber);
            tv_number.setText(datas.get(position));
            //不加view.是调用的当前整个activity(MainActivity)
            view.findViewById(R.id.ib_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datas.remove(position);
                    //更新界面
                    notifyDataSetChanged();
                    if(datas.size() == 0) {
                        //如果删除的是最后一条,隐藏popupwindow
                        popupWindow.dismiss();
                    }
                }
            });
            return view;
        }
    }
}
