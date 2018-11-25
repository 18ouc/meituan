package com.example.lenovo_pc;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main11Activity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private List<FoodT> mFoodList = new ArrayList<>();
    static private String abc8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        //接受数据
        Intent intent = getIntent();
        String accountFrom8 = intent.getStringExtra("account8");
        abc8 = accountFrom8;
        //接受数据

        final TextView tv_ts11=(TextView)findViewById(R.id.tv_ts11);
        tv_ts11.setText("订单里什么食物都没有唉~");
        initData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        FoodAdapterT foodAdapterT = new FoodAdapterT(mFoodList);
        recyclerView.setAdapter(foodAdapterT);
        FoodAdapterT.setOnItemClickListener(new FoodAdapterT.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                FoodT foodT = mFoodList.get(position);
                Toast.makeText(Main11Activity.this, "您所购买的是"+foodT.getDM()+"店铺的食物哦~", Toast.LENGTH_SHORT).show();
            }
        } );



        Button btn_11 = (Button)findViewById(R.id.btn_11);
        btn_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main11Activity.this,Main3Activity.class);
                intent.putExtra("account83", abc8);
                startActivity(intent);
                Main11Activity.this.finish();
            }
        });


    }
    private void initData() {
        Intent intent = getIntent();
        final TextView tv_ts11=(TextView)findViewById(R.id.tv_ts11);
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Menu", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String food_name = cursor.getString(cursor.getColumnIndex("food_name"));
                String food_price = cursor.getString(cursor.getColumnIndex("food_price"));
                String food_DM = cursor.getString(cursor.getColumnIndex("shop_name"));
                String checkaccount =cursor.getString(cursor.getColumnIndex("account"));
                if(checkaccount.equals(abc8)){
                    FoodT foodT= new FoodT();
                    tv_ts11.setText("欢迎使用美团~");
                    foodT.setNameF(food_name);
                    foodT.setPrice(food_price);
                    foodT.setImageIdF(R.drawable.buweilogo2);
                    foodT.setDM(food_DM);
                    mFoodList.add(foodT);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();     //用过之后记得调用cursor的close函数
    }
}
