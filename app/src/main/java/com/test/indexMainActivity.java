package com.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class indexMainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearlayout_id;
    private TextView open_close,u_Name,u_Value;
    private TextView open1_close;
    private TextView user_info;
//    private TextView tv;
//    Drawable icon1;
    private int height;
    private ImageView ImageView1;
    private ImageView ImageView2;
    String uname;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_main1);
        Button Quitbutton = findViewById(R.id.Quitbutton);

        //数据传递
        u_Name = findViewById(R.id.user_name);
        u_Value = findViewById(R.id.user_val);
        Bundle bundle =getIntent().getExtras();
        uname =bundle.getString("uname");
        String phone =bundle.getString("phone");
        u_Name.setText(uname);
        u_Value.setText(phone);
        open1_close =  findViewById(R.id.open1_close);
        open1_close.setOnClickListener(this::onClick);
        user_info = findViewById(R.id.user_info);
        user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(indexMainActivity.this,editmyinfo.class);
                startActivity(intent);
            }
        });
        Quitbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(indexMainActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        linearlayout_id =  findViewById(R.id.linearlayout_id);
        ImageView1 = findViewById(R.id.h_back);
        ImageView2 = findViewById(R.id.h_head);
//        tv = findViewById(R.id.user_info);
//        icon1 = getResources().getDrawable(R.drawable.ic_sign_name);
//        icon1.setBounds(0, 0, 80, 80);
//        tv.setCompoundDrawables(icon1, null, null, null);
        open_close =  findViewById(R.id.open_close);
        open_close.setOnClickListener(this::onClick);

//        initOnPreDrawListener();
//        Glide.with(this).load(R.drawable.head)
//                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
//                .into(ImageView1);
//
//        Glide.with(this).load(R.drawable.head)
//                .bitmapTransform(new CropCircleTransformation(this))
//                .into(ImageView2);

        /**
         * 临时跳转到数据页面*********************************************
         */
        ImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册界面
                Intent intent = new Intent(indexMainActivity.this, DataMonitorActivity.class);
                //数据传递
                Bundle bundle =new Bundle();
                bundle.putString("uname",uname);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_close:
                if (linearlayout_id.getVisibility() == View.GONE) {
                    //调用开始的方法
                    start();
                } else {
                    //调用结束的方法
                    end();
                }break;
            case R.id.open1_close:
                if (linearlayout_id.getVisibility()==View.GONE){
                  start();
                }else{
                  end();
                }
                break;
        }
    }

    private void initOnPreDrawListener() {

        final ViewTreeObserver viewTreeObserver = this.getWindow().getDecorView().getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {

                height = linearlayout_id.getMeasuredHeight();

                // 移除OnPreDrawListener事件监听
                indexMainActivity.this.getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);

                //获取完高度后隐藏控件
                linearlayout_id.setVisibility(View.GONE);

                return true;
            }
        });

    }

    private void start() {

        // 显示控件
        linearlayout_id.setVisibility(View.VISIBLE);

        //开启平移动画
        TranslateAnimation startTranslateAnim = new TranslateAnimation(0, 0, -height, 0);
        startTranslateAnim.setDuration(500);

        //控件开始动画
        linearlayout_id.startAnimation(startTranslateAnim);

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

    private void end() {
        // 隐藏控件
        linearlayout_id.setVisibility(View.GONE);

        // 关闭平移动画
        TranslateAnimation endTranslateAnim = new TranslateAnimation(0, 0, 0, -height);
        endTranslateAnim.setDuration(500);

        //控件开始动画
        linearlayout_id.startAnimation(endTranslateAnim);

        //关闭动画的监听
        endTranslateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始调用
            }

            @Override

            public void onAnimationEnd(Animation animation) {
                //动画结束时调用
                linearlayout_id.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画重复时调用

            }
        });
    }
}