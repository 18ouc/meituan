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

public class Main9Activity extends AppCompatActivity {

    private String Shop_Name91 , Shop_Name92,accountFrom89;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);


        //接受传入数据
        Intent intent = getIntent();
        final String accountFrom89 = intent.getStringExtra("account89");


        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！


        final EditText et_shop_Name91 = (EditText) findViewById(R.id.et_shop_Name91);
        final EditText et_shop_Name92 = (EditText)findViewById(R.id.et_shop_Name92);
        Button btn_SCShop=(Button)findViewById(R.id.btn_SCShop);
        btn_SCShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将TextView内容转化为字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(Shop_Name91)){
                    Toast.makeText(Main9Activity.this, "请输入店铺名称哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(Shop_Name92)){
                    Toast.makeText(Main9Activity.this, "请再次输入店铺名称哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!Shop_Name91.equals(Shop_Name92)){
                    Toast.makeText(Main9Activity.this, "两次输入的店铺名称不相同哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!isExistUserName(Shop_Name91)){
                    Toast.makeText(Main9Activity.this, "此店铺不存在哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else if(isCompareAccount(accountFrom89,Shop_Name91)){
                    Toast.makeText(Main9Activity.this, "这不是你的店铺哦~", Toast.LENGTH_SHORT).show();
                    return;
                }else{Toast.makeText(Main9Activity.this, "删除店铺成功~", Toast.LENGTH_SHORT).show();
                    //添加数据到数据库
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("Shop", "shop_name = ?", new String[] {Shop_Name91});
                    //Toast.makeText(Main9Activity.this, "删除完成~", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Main9Activity.this,Main8Activity.class);
                    intent.putExtra("Shop_Name98", accountFrom89);
                    startActivity(intent);
                    //Toast.makeText(Main9Activity.this, "删除店铺成功~", Toast.LENGTH_SHORT).show();
                    Main9Activity.this.finish();
                }
            }
            //将TextView内容转化为字符串
            private void getEditString() {
                Shop_Name91 = et_shop_Name91.getText().toString().trim();
                Shop_Name92  = et_shop_Name92.getText().toString().trim();
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
            //判断这是不是你的店铺（不要妄想删除别人店铺的东西
            private boolean isCompareAccount(String Account,String Shop_Name){
                boolean same = true;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Shop",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String ShopName = cursor.getString(cursor.getColumnIndex("shop_name"));
                        String ShopAccount = cursor.getString(cursor.getColumnIndex("account"));
                        if(Shop_Name.equals(ShopName) && Account.equals(ShopAccount)) {//不是在改别人家的店铺
                            same = false;
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                }
                return same;
            }


        });
        Button btn_Back9 = (Button)findViewById(R.id.btn_Back9);
        btn_Back9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });



    }
}
