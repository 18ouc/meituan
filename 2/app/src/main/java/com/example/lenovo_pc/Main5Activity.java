package com.example.lenovo_pc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {
    private String Shop_Name , Shop_Location;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！

        //接受数据 实现账号储存到数据库中
        Intent intent = getIntent();
        final String accountFrom1 = intent.getStringExtra("account1");
        //接受数据

        final EditText et_Shop_Name = (EditText) findViewById(R.id.et_shop_Name);
        final EditText et_shop_Location = (EditText)findViewById(R.id.et_shop_Location);
        Button btn_AddShop=(Button)findViewById(R.id.btn_AddShop);

        Button btn_Back5 = (Button)findViewById(R.id.btn_Back5);
        btn_Back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        btn_AddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将TextView内容转化为字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(Shop_Name)){
                    Toast.makeText(Main5Activity.this, "忘记输入店铺名称啦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(Shop_Location)){
                    Toast.makeText(Main5Activity.this, "忘记输入店铺位置啦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExistUserName(Shop_Name)){
                    Toast.makeText(Main5Activity.this, "此店铺已经存在啦~", Toast.LENGTH_SHORT).show();
                    return;
                }else{Toast.makeText(Main5Activity.this, "添加店铺成功~", Toast.LENGTH_SHORT).show();
                    //添加数据到数据库
                    dbHelper.getWritableDatabase();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("shop_name", Shop_Name);
                    values.put("shop_location", Shop_Location);
                    values.put("account",accountFrom1);
                    db.insert("Shop", null, values);
                    values.clear();
                    //添加数据到数据库
                    Toast.makeText(Main5Activity.this, "添加店铺成功~", Toast.LENGTH_SHORT).show();
                    //实现注册后的账号在登录界面上显示

                    //关闭3活动
                    if(Main3Activity.instance != null){
                        Main3Activity.instance.finish();
                    }

                    Intent intent = new Intent(Main5Activity.this,Main3Activity.class);
                    /*intent.putExtra("Shop_Name", Shop_Name);
                    intent.putExtra("Shop_Location",Shop_Location);*/
                    intent.putExtra("Account",accountFrom1);
                    startActivity(intent);
                    Main5Activity.this.finish();
                }
            }
            //将TextView内容转化为字符串
            private void getEditString() {
                Shop_Name = et_Shop_Name.getText().toString().trim();
                Shop_Location = et_shop_Location.getText().toString().trim();
            }
            //将TextView内容转化为字符串

            //从SQL中读取输入的用户名，判断SQL中是否有此店铺
            private boolean isExistUserName(String Shop_Name){
                boolean has_Shop_Name = false;
                //SQL查询是否有这个账号
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Shop",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String ShopCheck = cursor.getString(cursor.getColumnIndex("shop_name"));
                        if(Shop_Name.equals(ShopCheck)) {//如果店铺名称存在 则确实保存过这个店铺名称
                            has_Shop_Name = true;
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                }
                return has_Shop_Name;
            }


        });

    }

}
