package com.test.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.DataMonitorActivity;
import com.test.MainActivity;
import com.test.R;
import com.test.editmyinfo;


public class My_Fragment extends Fragment implements View.OnClickListener{
    private LinearLayout linearlayout_id,linearlayout_id1;
    private TextView open_close,u_Name,u_Value;
    private TextView open1_close;
    private TextView user_info;
    private int height;
    private ImageView ImageView1;
    private ImageView ImageView2;
    private Button Quitbutton;
    String uname;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  View view=inflater.inflate(R.layout.fragment_my,false); 都可
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        //正确使用findViewById，使用view调用，view需要先使用inflate定义
        Log.v("111111111","11111111");
        //退出登录部分
        Quitbutton = view.findViewById(R.id.Quitbutton);
        Log.v("222","11111111");
        Quitbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        u_Name =view.findViewById(R.id.user_name);
        u_Value = view.findViewById(R.id.user_val);
        Bundle bundle =getActivity().getIntent().getExtras();
        uname =bundle.getString("uname");
        String phone =bundle.getString("phone");
        u_Name.setText(uname);
        u_Value.setText(phone);
        open1_close =  view.findViewById(R.id.open1_close);
        open1_close.setOnClickListener(this::onClick);
        //个人信息修改
        user_info = view.findViewById(R.id.user_info);
        user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), editmyinfo.class);
                //数据传递
                Bundle bundle =getActivity().getIntent().getExtras();
                bundle.putString("uname",uname);
                bundle.putString("phone",phone);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        linearlayout_id = view.findViewById(R.id.linearlayout_id);
        linearlayout_id1 = view.findViewById(R.id.linearlayout_id1);
        ImageView1 = view.findViewById(R.id.h_back);
        ImageView2 =view.findViewById(R.id.h_head);
        open_close =  view.findViewById(R.id.open_close);
        open_close.setOnClickListener(this::onClick);
//        initOnPreDrawListener();
//        Glide.with(getContext()).load(R.drawable.head)
//                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop((BitmapPool) this))
//                .into(ImageView1);

//        Glide.with(this).load(R.drawable.head)
//  2              .bitmapTransform(new CropCircleTransformation((BitmapPool) this))
//                .into(ImageView2);
//        initOnPreDrawListener();
//        Glide.with(getActivity()).load(R.drawable.head)
//                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop((BitmapPool) this))
//                .into(ImageView1);
//
//        Glide.with(getActivity()).load(R.drawable.head)
//                .bitmapTransform(new CropCircleTransformation((BitmapPool) this))
//                .into(ImageView2);

//        /**
//         * 临时跳转到数据页面*********************************************
//         */
//        ImageView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 跳转到注册界面
//                Intent intent = new Intent(getActivity(), DataMonitorActivity.class);
//                //数据传递
//                Bundle bundle =new Bundle();
//                bundle.putString("uname",uname);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_close:
                if (linearlayout_id.getVisibility() == View.GONE) {
                    //调用开始的方法
                    start(linearlayout_id);
                } else {
                    //调用结束的方法
                    end(linearlayout_id);
                }break;
            case R.id.open1_close:
                if (linearlayout_id1.getVisibility()==View.GONE){
                    start(linearlayout_id1);
                }else{
                    end(linearlayout_id1);
                }
                break;
        }
    }
    private void initOnPreDrawListener() {

        final ViewTreeObserver viewTreeObserver = this.getActivity().getWindow().getDecorView().getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {

                height = linearlayout_id.getMeasuredHeight();

                // 移除OnPreDrawListener事件监听
                My_Fragment.this.getActivity().getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);

                //获取完高度后隐藏控件
                linearlayout_id.setVisibility(View.GONE);

                return true;
            }
        });

    }

    private void start(LinearLayout linear) {

        // 显示控件
        linear.setVisibility(View.VISIBLE);

        //开启平移动画
        TranslateAnimation startTranslateAnim = new TranslateAnimation(0, 0, -height, 0);
        startTranslateAnim.setDuration(500);

        //控件开始动画
        linear.startAnimation(startTranslateAnim);

        //开启动画的监听
        startTranslateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始调用
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束时调用
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画重复时调用
            }
        });
    }

    private void end(LinearLayout linear) {
        // 隐藏控件
        linear.setVisibility(View.GONE);

        // 关闭平移动画
        TranslateAnimation endTranslateAnim = new TranslateAnimation(0, 0, 0, -height);
        endTranslateAnim.setDuration(500);

        //控件开始动画
        linear.startAnimation(endTranslateAnim);
        

        //关闭动画的监听
        endTranslateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始调用
            }

            @Override

            public void onAnimationEnd(Animation animation) {
                //动画结束时调用
                linear.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画重复时调用

            }
        });
    }
}