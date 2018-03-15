package com.example.a74021.project1.activity_and_fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a74021.project1.R;
import com.example.a74021.project1.helper.NoteDateBaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 74021 on 2018/1/20.
 */

public class noteEdit extends Activity implements View.OnClickListener{
    private TextView tv_date;
    private EditText et_content;
    private Button btn_ok;
    private Button btn_cancel;
    private NoteDateBaseHelper DBHelper;
    private int enter_state=0;//区分是新建笔记还是更改原来的笔记
    public String last_content;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.edit);

        InitView();
    }
    private void InitView()
    {
        tv_date=(TextView)findViewById(R.id.tv_date);
        et_content=(EditText)findViewById(R.id.et_content);
        btn_ok=(Button)findViewById(R.id.btn_ok);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);
        DBHelper=new NoteDateBaseHelper(this);

        //获取系统时间
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dataString=sdf.format(date);
        tv_date.setText(dataString);

        //接收内容id
        Bundle myBundle =this.getIntent().getExtras();
        last_content=myBundle.getString("info");
        enter_state=myBundle.getInt("enter_state");
        et_content.setText(last_content);

        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_ok:
                SQLiteDatabase db=DBHelper.getReadableDatabase();
                //获取editText内容
                String content=et_content.getText().toString();

                //添加日志
                if (enter_state==0) {
                    if (!content.equals("")) {
                        //截获时间
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String dataString = sdf.format(date);

                        //添加到数据库
                        ContentValues values = new ContentValues();
                        values.put("content", content);
                        values.put("date", dataString);
                        db.insert("note", null, values);
                        finish();
                    } else {
                        Toast.makeText(noteEdit.this, "在这里输入内容!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    ContentValues values=new ContentValues();
                    values.put("content",content);
                    db.update("note",values,"content = ?",new String[]{last_content});
                    finish();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
}
