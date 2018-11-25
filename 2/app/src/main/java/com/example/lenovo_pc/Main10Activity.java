package com.example.lenovo_pc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main10Activity extends AppCompatActivity {
    private String Food_Name , Food_Price;
    private MyDatabaseHelper dbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        //接受数据 实现账号储存到数据库中
        Intent intent = getIntent();
        final String accountFrom710 = intent.getStringExtra("foodid");
        final String TaccountF710 = intent.getStringExtra("TaccountF");
        //接受数据

        final EditText et_food_Name = (EditText) findViewById(R.id.et_food_Name);
        final EditText et_food_Price = (EditText)findViewById(R.id.et_food_Price);
        Button btn_AddFood=(Button)findViewById(R.id.btn_AddFood);

        btn_AddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将TextView内容转化为字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(Food_Name)){
                    Toast.makeText(Main10Activity.this, "请输入食物名称哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(Food_Price)){
                    Toast.makeText(Main10Activity.this, "请输入食物价格哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExistUserName(Food_Name)){
                    Toast.makeText(Main10Activity.this, "此食物已经存在哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else{Toast.makeText(Main10Activity.this, "添加食物成功~", Toast.LENGTH_SHORT).show();
                    //添加数据到数据库
                    if(Main7Activity.instance != null){
                        Main7Activity.instance.finish();
                    }
                    dbHelper.getWritableDatabase();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("food_name ", Food_Name);
                    values.put("food_price ", Food_Price);
                    values.put("shop_name",accountFrom710);
                    db.insert("Food", null, values);
                    values.clear();
                    //添加数据到数据库
                    Toast.makeText(Main10Activity.this, "添加食物成功~", Toast.LENGTH_SHORT).show();
                    //实现注册后的账号在登录界面上显示

                    Intent intent = new Intent(Main10Activity.this,Main7Activity.class);
                    intent.putExtra("foodid107",accountFrom710);
                    intent.putExtra("TaccountF107",TaccountF710);
                    startActivity(intent);
                    Main10Activity.this.finish();
                }
            }
            //将TextView内容转化为字符串
            private void getEditString() {
                Food_Name = et_food_Name.getText().toString().trim();
                Food_Price = et_food_Price.getText().toString().trim();
            }
            //将TextView内容转化为字符串

            //从SQL中读取输入的用户名，判断SQL中是否有此店铺
            private boolean isExistUserName(String Shop_Name){
                boolean has_Food_Name = false;
                //SQL查询是否有这个账号
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Food",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String FoodCheck = cursor.getString(cursor.getColumnIndex("food_name"));
                        if(Food_Name.equals(FoodCheck)) {//如果店铺名称存在 则确实保存过这个店铺名称
                            has_Food_Name = true;
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                }
                return has_Food_Name;
            }


        });
        Button btn_Back10 = (Button)findViewById(R.id.btn_Back10);
        btn_Back10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}
