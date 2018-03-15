package com.example.a74021.project1.activity_and_fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a74021.project1.R;
import com.example.a74021.project1.activity_and_fragment.PictureDetail;
import com.example.a74021.project1.helper.GridSpacingItemDecoration;
import com.example.a74021.project1.helper.ItemClickSupport;

import static android.content.ContentValues.TAG;
import static com.example.a74021.project1.activity_and_fragment.MainActivity.mList;

/**
 * Created by 74021 on 2018/1/7.
 */

public class RecyclerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        return inflater.inflate(R.layout.fragment_picture,container,false);
    }
    @Override
    public void onActivityCreated(Bundle saveInsatanceState)
    {
        super.onActivityCreated(saveInsatanceState);
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recyclerview);
        //布局的内容组件




        class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView iv;
            public MyViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.iv);
            }
        }





        //适配器，继承RecyclerView.Adapter,泛型，里面必须是Recycler.ViewHolder的子类
        class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{


            // 一个页面没有加载完毕，会持续调用该方法。
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                //创建ViewHolder
                //导入布局
                View layout = View.inflate(getActivity().getBaseContext(),R.layout.item_layout,null);
                MyViewHolder viewHolder = new MyViewHolder(layout);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, final int position) {
                //为每一个布局设置属性。
                //holder  组件
                //当前第几项， mList.get(position)拿到数据，给holder设置

                holder.iv.setImageResource(mList.get(position).imgId);
            }

            @Override
            public int getItemCount() {
                return mList.size();
            }
        }



        //RecyclerView 瀑布流
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recyclerview);
        //初始化数据
         //
        //设置适配器
        final  MyAdapter myAdapter=new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));//与ListView类似
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //设置item分割线
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,10,true));
        //设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置点击事件和长按事件
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getActivity().getApplicationContext(),"recyclerView_ItemClick",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(getActivity(),PictureDetail.class);
                int Picid=mList.get(position).getImgId();
                intent.putExtra("pictureId",Picid);
                startActivity(intent);
                //设置系统自带的Acitivity切换动画
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                AlertDialog.Builder message=new AlertDialog.Builder(getActivity());
                message.setTitle("取下相片");
                message.setMessage("确定取下这张照片？");
                message.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface,int i){
                    }
                });
                message.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface,int i)
                    {
                        mList.remove(position);
                        myAdapter.notifyDataSetChanged();
                    }
                } );
                message.create().show();
                return true;
            }
        });
    }


    @Override
            public void onDestroy(){
                super.onDestroy();
                Log.d(TAG,"onDestroy");}
}

