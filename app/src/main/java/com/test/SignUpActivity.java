package com.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.dao.Consumer;
import com.test.dao.ConsumerDao;
import com.test.util.IdUtils;
import com.test.util.LongLog;

public class SignUpActivity extends Activity {
    EditText userName;
    EditText passWord;
    EditText passWordAgain;
    EditText phone;

    private Handler mainhandler;
    private ConsumerDao consumerDao;
    // 调用Actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关联activity_register.xml
        setContentView(R.layout.activity_sign_up);
        // 关联用户名、密码、确认密码、邮箱和注册、返回登录按钮
         userName = findViewById(R.id.UserNameEdit2);
         passWord = findViewById(R.id.PassWordEdit2);
         passWordAgain = findViewById(R.id.PassWordAgainEdit);
         phone = findViewById(R.id.EmailEdit);
        Button signUpButton = (Button) this.findViewById(R.id.SignUpButton);
        Button backLoginButton = (Button) this.findViewById(R.id.BackLoginButton);
        consumerDao =new ConsumerDao();
        mainhandler = new Handler(getMainLooper());//获取主线程

        // 立即注册按钮监听器
        signUpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strUserName = userName.getText().toString().trim();
                        String strPassWord = passWord.getText().toString().trim();
                        String strPassWordAgain = passWordAgain.getText().toString().trim();
                        String strPhoneNumber = phone.getText().toString().trim();
                        //注册格式粗检
                        if (strUserName.length() > 10) {
                            Toast.makeText(SignUpActivity.this, "用户名长度必须小于10！", Toast.LENGTH_SHORT).show();
                        } else if (strUserName.length() < 1) {
                            Toast.makeText(SignUpActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                        } else if (strPassWord.length() > 16) {
                            Toast.makeText(SignUpActivity.this, "密码长度必须小于16！", Toast.LENGTH_SHORT).show();
                        } else if (strPassWord.length() < 6) {
                            Toast.makeText(SignUpActivity.this, "密码长度最低为6位！", Toast.LENGTH_SHORT).show();
                        } else if (!strPassWord.equals(strPassWordAgain)) {
                            Toast.makeText(SignUpActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
//                        } else if (!strPhoneNumber.contains("@")) {
//                            Toast.makeText(SignUpActivity.this, "邮箱格式不正确！", Toast.LENGTH_SHORT).show();
                        }else if (strPhoneNumber.length()!=11) {
                            Toast.makeText(SignUpActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                        } else {
                            doRgister();
                        }
                    }
                }
        );
        // 返回登录按钮监听器
        backLoginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到登录界面
                        //原来的代码段Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }

    //注册
    private void doRgister() {
        // 获取用户注册信息
        String strUserName2 = userName.getText().toString().trim();
        String strPassWord2 = passWord.getText().toString().trim();
        String strPhone2 = phone.getText().toString().trim();
        String strAccount2 = IdUtils.getPrimaryKey();
        LongLog.printMsg(strAccount2+"随机生成的10位账号id---------------------");
        Consumer consumer = new Consumer();
        consumer.setAccount(strAccount2);
        consumer.setUname(strUserName2);
        consumer.setPassword(strPassWord2);
        consumer.setPhone(strPhone2);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Consumer consumer1 = consumerDao.findByUname(strUserName2);
                    LongLog.printMsg(String.valueOf(consumer1) + "判断是否已经存在用户名++++++++++++++++++");
                            if (consumer1 == null) {
                                int res = consumerDao.resgister(consumer);
                                LongLog.printMsg("注册返回行数+++++++++++" + res);
                                mainhandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (res == 1) {
                                            Toast.makeText(SignUpActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                            // 跳转到登录界面
                                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(SignUpActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });

                            }
                            else{
                                Toast.makeText(SignUpActivity.this, "用户名已存在！", Toast.LENGTH_SHORT).show();
                            }
                    Looper.loop();
                }
            }).start();
        }
}
