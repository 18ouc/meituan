package com.example.lenovo_pc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class Main8Activity extends AppCompatActivity {




    private MyDatabaseHelper dbHelper;
    private List<Shop> mShopList = new ArrayList<>();
    static private String abc8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        //接受数据
        Intent intent = getIntent();
        String accountFrom8 = intent.getStringExtra("account8");
        abc8 = accountFrom8;
        //接受数据
       // String accountFrom98 = intent.getStringExtra("account98");
        //if(abc8.equals("")) {
        //    abc8 = accountFrom98;
       // }



        final TextView tv_ts8=(TextView)findViewById(R.id.tv_ts8);
        tv_ts8 .setText("快点儿添加你的店铺吧~");


        Button btn_Shanchu8 = (Button)findViewById(R.id.btn_Shanchu8);
        Button btn_Fanhui8 = (Button)findViewById(R.id.btn_Fanhui8);
       //RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        initData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ShopAdapter shopAdapter = new ShopAdapter(mShopList);
        recyclerView.setAdapter(shopAdapter);

        ShopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Main8Activity.this, Main7Activity.class);
                //intent.putExtra("name",list.get(position).toString());
                startActivity(intent);
            }
        } );


        btn_Fanhui8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main8Activity.this,Main3Activity.class);
                intent.putExtra("account83", abc8);
                startActivity(intent);
                Main8Activity.this.finish();
            }
        });

        btn_Shanchu8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main8Activity.this,Main9Activity.class);
                intent.putExtra("account89", abc8);
                startActivity(intent);
            }
        });
    }





//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR

    private void initData() {
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Shop", null, "account==?", new String[]{abc8}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String shop_name = cursor.getString(cursor.getColumnIndex("shop_name"));
                String shop_location = cursor.getString(cursor.getColumnIndex("shop_location"));
                String u_a = cursor.getString(cursor.getColumnIndex("account"));
                Shop shop = new Shop();
                if (shop_name == null) {
                    shop.setName("目前没有店铺");
                } else {
                        final TextView tv_ts8=(TextView)findViewById(R.id.tv_ts8);
                        tv_ts8 .setText("美团，为食物而生~");
                        shop.setName(shop_name);
                        shop.setLocation(shop_location);
                        shop.setImageId(R.drawable.buweilogo);
                        shop.setZH(u_a);
                        mShopList.add(shop);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();     //用过之后记得调用cursor的close函数
    }
}
