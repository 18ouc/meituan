package com.example.lenovo_pc;

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

public class Main12Activity extends AppCompatActivity {
    private String Food_Name91 , Food_Name92;

    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);


        //接受传入数据
        Intent intent = getIntent();
        final String accountFrom712 = intent.getStringExtra("shop_name");


        final String TaccountF712= intent.getStringExtra("TaccountF");


        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！


        final EditText et_food_Name91 = (EditText) findViewById(R.id.et_food_Name91);
        final EditText et_food_Name92 = (EditText)findViewById(R.id.et_food_Name92);
        Button btn_SCFood=(Button)findViewById(R.id.btn_SCFood);
        btn_SCFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将TextView内容转化为字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(Food_Name91)){
                    Toast.makeText(Main12Activity.this, "请输入食物名称哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(Food_Name92)){
                    Toast.makeText(Main12Activity.this, "请再次输入食物名称哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!Food_Name91.equals(Food_Name92)){
                    Toast.makeText(Main12Activity.this, "两次输入的食物名称不相同哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!isExistUserName(Food_Name91)){
                    Toast.makeText(Main12Activity.this, "此食物不在该店哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(isCompareAccount(Food_Name91,accountFrom712)){
                    Toast.makeText(Main12Activity.this, "此食物不在该店哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else{Toast.makeText(Main12Activity.this, "删除食物成功~", Toast.LENGTH_SHORT).show();
                    //添加数据到数据库
                    if(Main7Activity.instance != null){
                        Main7Activity.instance.finish();
                    }
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("Food", "food_name = ?", new String[] {Food_Name91});
                    //Toast.makeText(Main9Activity.this, "删除完成~", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Main12Activity.this,Main7Activity.class);
                    intent.putExtra("Shop_Name127", accountFrom712);
                    intent.putExtra("Taccount127",TaccountF712);
                    startActivity(intent);
                    Toast.makeText(Main12Activity.this, "删除食物成功~", Toast.LENGTH_SHORT).show();
                    Main12Activity.this.finish();
                }
            }
            //将TextView内容转化为字符串
            private void getEditString() {
                Food_Name91 = et_food_Name91.getText().toString().trim();
                Food_Name92  = et_food_Name92.getText().toString().trim();
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
        Button btn_Back12 = (Button)findViewById(R.id.btn_Back12);
        btn_Back12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

    }
}
