package com.example.lenovo_pc;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String Account1,psw1;
    private MyDatabaseHelper dbHelper;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_Register=(Button)findViewById(R.id.btn_Register);
        Button btn_Leave = (Button)findViewById(R.id.btn_Leave);
        Button btn_Forget = (Button)findViewById(R.id.btn_Forget);
        Button btn_Log = (Button)findViewById(R.id.btn_Log);


        CheckBox cb_password = (CheckBox)findViewById(R.id.cb_password);

        final EditText et_Password=(EditText)findViewById(R.id.et_Password);
        final EditText et_Account = (EditText)findViewById(R.id.et_Account);
        dbHelper = new MyDatabaseHelper(this,"Store.db",null,1);
        //接受数据
            Intent intent = getIntent();
            String accountFrom = intent.getStringExtra("account");
            et_Account.setText(accountFrom);
            final String PswFrom = intent.getStringExtra("psw");
            et_Password.setText(PswFrom);
            if(accountFrom != null) et_Account.setSelection(accountFrom.length());
        //接受数据

        //点击注册发生的事件
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);

            }
        });

        //点击登录发生的事件
        btn_Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获得EditView中的输入
                getEditString();
                if (TextUtils.isEmpty(Account1)) {
                    Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw1)) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (match(Account1, MD5.md5(psw1))) {// md5Psw.equals(); 判断，输入的密码加密后，是否与保存在数据库中一致
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //Intent data = new Intent();
                    //data.putExtra("isLogin", true);//RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                   // setResult(RESULT_OK, data);

                    //实现将账号导入登录界面(导入)
                   Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                   intent.putExtra("account1", Account1);
                    startActivity(intent);
                    MainActivity.this.finish();
                    //intent.putExtra("psw",psw);

                    //实现将账号导入登录界
                    //MainActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 Main3Activity 中
                   // startActivity(new Intent(MainActivity.this, Main3Activity.class));
                    //return;
                } else if (psw1 != null && !TextUtils.isEmpty(psw1) && !match(Account1, MD5.md5(psw1))) {
                    Toast.makeText(MainActivity.this, "输入的帐号和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(MainActivity.this, "此账号不存在", Toast.LENGTH_SHORT).show();
                }

            }
            //将EditView内容转化为字符
            private void getEditString() {
                Account1 = et_Account.getText().toString().trim();
                psw1 = et_Password.getText().toString().trim();
            }


            //判断账号和密码是否匹配
            private boolean match(String Account,String Mpsw){
                boolean match = false;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("User",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String AccountCheck = cursor.getString(cursor.getColumnIndex("account"));
                        String PswCheck = cursor.getString(cursor.getColumnIndex("password"));
                        if(Account.equals(AccountCheck) && Mpsw.equals(PswCheck)) {//如果账号存在 则确实保存过这个用户名
                            match = true;
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                }
                return match;
            }
        });
        //找回密码控件的点击事件
        btn_Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main4Activity.class);
                startActivity(intent);
            }
        });


        btn_Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.instance != null){
                    MainActivity.instance.finish();
                }
                MainActivity.this.finish();
                Toast.makeText(MainActivity.this, "美团~感谢您的使用", Toast.LENGTH_SHORT).show();
            }
        });


        cb_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et_Password.setInputType(0x90);
                    et_Password.setSelection(et_Password.length());
                }
                else{
                    et_Password.setInputType(0x81);
                    et_Password.setSelection(et_Password.length());
                }
            }
        });





    }
}

























/*
    Button btn_Leave = (Button)findViewById(R.id.btn_Leave);
    Button btn_Forget = (Button)findViewById(R.id.btn_Forget);
    Button btn_Log = (Button)findViewById(R.id.btn_Log);
    final EditText et_Account = (EditText)findViewById(R.id.et_Account);
    final EditText et_Password = (EditText)findViewById(R.id.et_Password);
        btn_Leave.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        //登录界面销毁
        MainActivity.this.finish();
        }
        });

        //找回密码控件的点击事件
        btn_Forget.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        //跳转到找回密码界面（此页面暂未创建）
        }
        });
        //登录按钮的点击事件
        btn_Log.setOnClickListener(new View.OnClickListener() {
private String Account , psw,spPsw;

@Override
public void onClick(View v) {
        String md5Psw= MD5.md5(psw);
        //Account =et_Account.getText().toString().trim();//开始登录，获取用户名和密码 getText().toString().trim()
        //psw=et_Password.getText().toString().trim();// 定义方法 readPsw为了读取用户名，得到密码
        spPsw=readPsw(Account);
               /* if(TextUtils.isEmpty(Account)){
                    Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }else if(md5Psw .equals(spPsw)){
                    //一致登录成功
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, Account);
                    //登录成功后关闭此页面进入主页
                    Intent data=new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin",true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK,data);
                    //销毁登录界面
                    MainActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 Main3Activity 中
                    startActivity(new Intent(MainActivity.this, Main3Activity.class));
                    return;
                }else if((spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw))){
                    Toast.makeText(MainActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(MainActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }*/
   /*     }
        });
        }*/
//从SharedPreferences中根据用户名读取密码
/*
private String readPsw(String Account){
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(Account , "");
        }


private void saveLoginStatus(boolean status,String userName){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
        }

    /* @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                et_Account.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
    }
 */
