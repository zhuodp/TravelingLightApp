package com.example.a74021.project1.activity_and_fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a74021.project1.R;
import com.example.a74021.project1.activity_and_fragment.noteEdit;
import com.example.a74021.project1.helper.NoteDateBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 74021 on 2018/1/20.
 */

public class settingFragment extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private ListView listView;
    private SimpleAdapter simple_adapter;
    private List<Map<String,Object>> dataList;
    private Button addNote;
    private TextView tv_content;
    private NoteDateBaseHelper DBHelper;
    private SQLiteDatabase DB;
    @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState)
        {
            return inflater.inflate(R.layout.fragment_settings,container,false);
        }
    @Override
    public void onActivityCreated(Bundle saveInsatanceState) {
        super.onActivityCreated(saveInsatanceState);

        tv_content=(TextView)getActivity().findViewById(R.id.tv_content);
        listView=(ListView)getActivity().findViewById(R.id.listview);
        dataList=new ArrayList<Map<String,Object>>();
        addNote=(Button)getActivity().findViewById(R.id.btn_editnote);
        DBHelper=new NoteDateBaseHelper(getActivity());
        DB=DBHelper.getReadableDatabase();

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),noteEdit.class);
                Bundle bundle=new Bundle();
                bundle.putString("info","");
                bundle.putInt("enter_state",0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onStart(){
        super.onStart();
        RefreshNoteList();
    }

    //更新listView
    public void RefreshNoteList() {
        if (dataList != null) {
            //如果dataList已经有内容，则全部删掉并且更新simple_adapter
            int size = dataList.size();
            if (size > 0) {
                dataList.removeAll(dataList);
                simple_adapter.notifyDataSetChanged();
            }

            //从数据库读取信息
            Cursor cursor = DB.query("note", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("content"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tv_content", name);
                map.put("tv_date", date);
                dataList.add(map);
            }
            simple_adapter = new SimpleAdapter(getActivity().getApplicationContext(), dataList, R.layout.item,
                    new String[]{"tv_content", "tv_date"}, new int[]{
                    R.id.tv_content, R.id.tv_date});
            listView.setAdapter(simple_adapter);
        }
    }

        //单击时间
        @Override
        public void onItemClick (AdapterView < ? > arg0, View arg1,int arg2, long arg3){
            String content = listView.getItemAtPosition(arg2) + "";
            String content1 = content.substring(content.indexOf("=") + 1, content.indexOf(","));

            Intent myIntent = new Intent(getActivity(), noteEdit.class);
            Bundle bundle = new Bundle();
            bundle.putString("info", content1);
            bundle.putInt("enter_state", 1);
            myIntent.putExtras(bundle);
            startActivity(myIntent);
        }

        //长按事件
        @Override
        public boolean onItemLongClick (AdapterView < ? > arg0, View arg1,final int arg2, long arg3)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("删除该日志");
            builder.setMessage("确认删除？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String content = listView.getItemAtPosition(arg2) + "";
                    String content1 = content.substring(content.indexOf("=") + 1, content.indexOf(","));
                    DB.delete("note", "content = ?", new String[]{content1});
                    RefreshNoteList();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create();
            builder.show();
            return  true;
        }

}

