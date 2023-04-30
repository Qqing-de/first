package com.test;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.test.dao.Consumer;
import com.test.dao.ConsumerDao;
import com.test.dao.GapResult;
import com.test.util.LongLog;

import java.util.List;


public class editmyinfo extends AppCompatActivity {
    private TextView uname,sex,weight,phone,age;
    private ConsumerDao consumerDao;
    private RadioGroup radio;
    private RadioButton radioButton;
    private Handler mainhandler;
    private Consumer consumer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmyinfo);
        consumerDao =new ConsumerDao();
        mainhandler = new Handler(getMainLooper());//获取主线程
        uname =findViewById(R.id.uanme);
        radio =findViewById(R.id.radio);

        weight =findViewById(R.id.weight);
        phone =findViewById(R.id.phone);
        age =findViewById(R.id.age);
        Bundle bundle =getIntent().getExtras();
        String u_name =bundle.getString("uname");
        String p_hone =bundle.getString("phone");
        uname.setText(u_name);
        phone.setText(p_hone);
        //获取用户信息
        String strUserName = uname.getText().toString().trim();
        consumer1= consumerDao.findByUname(strUserName);
        LongLog.printMsg(consumer1+"获取用户信息-----------");
//        补全用户信息
        String sex1=consumer1.getSex();
        String weight1= String.valueOf(consumer1.getWeight());
        String age1= String.valueOf(consumer1.getAge());
//        sex.setText(sex1);
        weight.setText(weight1);
        age.setText(age1);

        Button button = findViewById(R.id.serveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMsg();
            }

        });

        TextView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editmyinfo.this,TestActivity.class);
                //数据传递
                Bundle bundle =new Bundle();
                bundle.putString("uname", u_name);
                bundle.putString("phone", p_hone);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void editMsg() {
        //给RadioGroup设置选中项改变监听
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            }
        });
        radioButton = findViewById(radio.getCheckedRadioButtonId());
        // 获取radiobutton的值
        String sex0 = radioButton.getText().toString();
        LongLog.printMsg(sex0+"---radio得性别");
        // 获取用户名
        String strUserName = uname.getText().toString().trim();
        String str_phone=phone.getText().toString().trim();
        String str_sex=sex0.trim();
        int str_weight= Integer.parseInt(weight.getText().toString().trim());
        int str_age= Integer.parseInt(age.getText().toString().trim());


        Consumer consumer =new Consumer();
        consumer.setAccount(consumer1.getAccount());
        consumer.setPassword(consumer1.getPassword());
        consumer.setUname(strUserName);
        consumer.setSex(str_sex);
        consumer.setAge(str_age);
        consumer.setWeight(str_weight);
        consumer.setPhone(str_phone);
        consumer.setMeasure(consumer1.getMeasure());
        LongLog.printMsg("---consumer----要传入修改得用户信息---------------"+'\n'+consumer.toString());
        if (TextUtils.isEmpty(strUserName)){
            Toast.makeText(editmyinfo.this, "未登录！", Toast.LENGTH_SHORT).show();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    int tf = consumerDao.editMsg(consumer);
                    LongLog longLog = new LongLog();
                    mainhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (tf != 0) {
                                Toast.makeText(editmyinfo.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                Consumer consumer3= consumerDao.findByUname(strUserName);
                                LongLog.printMsg(consumer3+"-------修改后");
                                Intent intent = new Intent(editmyinfo.this, TestActivity.class);
                                //数据传递
                                Bundle bundle = new Bundle();
                                bundle.putString("uname", consumer.getUname());
                                bundle.putString("phone", consumer.getPhone());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(editmyinfo.this, "修改失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Looper.loop();
                }
            }).start();
        }
    }
}