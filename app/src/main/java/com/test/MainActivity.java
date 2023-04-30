package com.test;
        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Looper;
        import android.os.StrictMode;
        import android.text.TextUtils;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.test.dao.Consumer;
        import com.test.dao.ConsumerDao;
        import com.test.util.LongLog;

public class MainActivity extends Activity {
    Drawable icon1;
    Drawable icon2;

    private ConsumerDao consumerDao;
    private Handler mainhandler;
    EditText passWord;
    EditText userName;

    // 调用Actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 关联activity.xml
        setContentView(R.layout.activity_main);
        //网络申请权限加强
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // 关联用户名、密码和登录、注册按钮
         userName = (EditText) this.findViewById(R.id.UserNameEdit);
         passWord = (EditText) this.findViewById(R.id.PassWordEdit);

        consumerDao =new ConsumerDao();
        mainhandler = new Handler(getMainLooper());//获取主线程

        Button loginButton = (Button) this.findViewById(R.id.LoginButton);
        Button signUpButton = (Button) this.findViewById(R.id.SignUpButton);
        icon1 = getResources().getDrawable(R.drawable.login);
        icon2 = getResources().getDrawable(R.drawable.password);
        icon1.setBounds(0, 0, 80, 80);
        icon2.setBounds(0, 0, 80, 80);
        userName.setCompoundDrawables(icon1, null, null, null);
        passWord.setCompoundDrawables(icon2, null, null, null);
        // 登录按钮监听器
        loginButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doLogin();
                    }
                }
        );

        // 注册按钮监听器
        signUpButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到注册界面
                        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }


    //登录
    private void doLogin() {
        // 获取用户名和密码
        String strUserName = userName.getText().toString().trim();
        String strPassWord = passWord.getText().toString().trim();
        if (TextUtils.isEmpty(strUserName)){
            Toast.makeText(MainActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
            userName.requestFocus();
        }else if (TextUtils.isEmpty(strPassWord)){
            Toast.makeText(MainActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
            passWord.requestFocus();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Consumer consumer =consumerDao.login(strUserName,strPassWord);
                    LongLog longLog =new LongLog();
                    longLog.printMsg(String.valueOf(consumer));
                    mainhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (consumer!=null){
                                Toast.makeText(MainActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                                //数据传递
                                Bundle bundle =new Bundle();
                                bundle.putString("uname",consumer.getUname());
                                bundle.putString("phone",consumer.getPhone());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Looper.loop();
                }
            }).start();
        }
    }
}
