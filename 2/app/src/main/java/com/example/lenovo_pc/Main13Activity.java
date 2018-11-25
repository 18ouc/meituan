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

public class Main13Activity extends AppCompatActivity {
    private String Food_Name131 , Food_Name132,Food_Price132;

    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);


        //接受传入数据
        Intent intent = getIntent();
        final String accountFrom713 = intent.getStringExtra("shop_name");


        final String TaccountF713= intent.getStringExtra("TaccountF");


        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！


        final EditText et_food_Name131 = (EditText) findViewById(R.id. et_food_Name131 );
        final EditText et_food_Name132 = (EditText)findViewById(R.id.et_food_Name132);
        final EditText et_food_Price132 = (EditText)findViewById(R.id.et_food_Price132);
        Button btn_GXFood=(Button)findViewById(R.id.btn_GXFood);
        btn_GXFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将TextView内容转化为字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(Food_Name131)){
                    Toast.makeText(Main13Activity.this, "请输入原食物名称哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(Food_Name132)){
                    Toast.makeText(Main13Activity.this, "请输入修改后食物名称哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(Food_Price132)) {
                    Toast.makeText(Main13Activity.this, "请输入修改后食物价格哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(Food_Name131.equals(Food_Name132)){
                    Toast.makeText(Main13Activity.this, "两次输入的店铺名称相同哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!isExistUserName(Food_Name131)){
                    Toast.makeText(Main13Activity.this, "此食物不在该店哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(isCompareAccount(Food_Name131,accountFrom713)){
                    Toast.makeText(Main13Activity.this, "此食物不在该店哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else{Toast.makeText(Main13Activity.this, "修改食物成功~", Toast.LENGTH_SHORT).show();
                    //添加数据到数据库
                    //关闭3活动
                    if(Main7Activity.instance != null){
                        Main7Activity.instance.finish();
                    }

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values  = new ContentValues();
                    values.put("food_name", Food_Name132);
                    values.put("food_price", Food_Price132);
                    db.update("Food", values, "food_name = ?", new String[] {Food_Name131});
                    Toast.makeText(Main13Activity.this, "修改食物成功~", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(Main13Activity.this,Main7Activity.class);
                    intent.putExtra("Shop_Name137", accountFrom713);
                    intent.putExtra("Taccount137",TaccountF713);
                    startActivity(intent);
                    Main13Activity.this.finish();
                }
            }
            //将TextView内容转化为字符串
            private void getEditString() {
                Food_Name131 = et_food_Name131.getText().toString().trim();
                Food_Name132  = et_food_Name132.getText().toString().trim();
                Food_Price132= et_food_Price132.getText().toString().trim();
            }
            //将TextView内容转化为字符串

            //从SQL中读取输入的用户名，判断SQL中是否有此店铺
            private boolean isExistUserName(String Food_Name){
                boolean has_Food_Name = false;
                //SQL查询是否有这个账号
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Food",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String ShopCheck = cursor.getString(cursor.getColumnIndex("food_name"));
                        if(Food_Name.equals(ShopCheck)) {//如果店铺名称存在 则确实保存过这个店铺名称
                            has_Food_Name = true;
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                }
                return has_Food_Name;
            }
            //判断这是不是你的店铺（不要妄想删除别人店铺的东西
            private boolean isCompareAccount(String Food_Name,String Shop_Name){
                boolean same = true;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Food",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String ShopName = cursor.getString(cursor.getColumnIndex("shop_name"));
                        String FoodName = cursor.getString(cursor.getColumnIndex("food_name"));
                        if(Food_Name.equals(FoodName) && Shop_Name.equals(ShopName)) {//不是在改别人家的食物
                            same = false;
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                }
                return same;
            }


        });
        Button btn_Back13 = (Button)findViewById(R.id.btn_Back13);
        btn_Back13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

    }
}
