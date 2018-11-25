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

public class Main7Activity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private List<FoodT> mFoodList = new ArrayList<>();
    private static String abc;
    private static String adc;


    //实现在5活动中关闭3活动
             public static Main7Activity instance;
    //做一个对外的接口
    private Main7Activity.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(Main7Activity.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    //做一个对外的接口


    //RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        //这句话千万不能少！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！

        Button btn_AddFOOD = (Button)findViewById(R.id.btn_AddFOOD);
        Button btn_ShanchuFOOD=(Button)findViewById(R.id.btn_ShanchuFOOD);
        Button btn_GenxinFOOD=(Button)findViewById(R.id.btn_GenxinFOOD);
        Button btn_BAF=(Button)findViewById(R.id.btn_BAF);

        Intent intent = getIntent();
        final String accountFrom37 = intent.getStringExtra("shop_Name37");
        abc = accountFrom37;
        final String accountFrom107 =intent.getStringExtra("foodid107");

        final String TaccountF = intent.getStringExtra("account_37");
        adc = TaccountF;
        final String accountFrom127 =intent.getStringExtra("Shop_Name127");

        final String TaccountF710 =intent.getStringExtra("TaccountF710");

        final String TaccountF127 =intent.getStringExtra("TaccountF127");

        final String accountFrom137 =intent.getStringExtra("Shop_Name137");

        final String Taccount137 =intent.getStringExtra("Taccount137");
        if(abc.equals("")&& !accountFrom107.equals("")) abc = accountFrom107;
        if(abc.equals("")&& accountFrom107.equals("")&& !accountFrom127.equals("")) abc = accountFrom127;

        if(adc.equals("")&& !TaccountF710.equals("")&&!TaccountF710.equals(""))  adc=TaccountF710;
        if(adc.equals("")&& TaccountF710.equals(""))  adc=TaccountF127;

        if(adc.equals("")&& TaccountF710.equals("")&&TaccountF710.equals("") &&!accountFrom137.equals(""))  adc=accountFrom137;
        if(adc.equals("")&&  TaccountF710.equals("")&& TaccountF710.equals(""))  adc=Taccount137;


        //Toast.makeText(Main7Activity.this, accountFrom37, Toast.LENGTH_SHORT).show();
        final TextView tv_ts7=(TextView)findViewById(R.id.tv_ts7);
        tv_ts7.setText("店铺里什么食物都没有唉~");
        initData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        FoodAdapterT foodAdapterT = new FoodAdapterT(mFoodList);
        recyclerView.setAdapter(foodAdapterT);
        FoodAdapterT.setOnItemClickListener(new FoodAdapterT.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                String shop_name = abc;
                final String account = TaccountF;

                final TextView tv_alert=(TextView)findViewById(R.id.tv_alert);
                final AlertDialog.Builder builder = new AlertDialog.Builder(Main7Activity.this);
                builder.setTitle( "尊敬的买家:" );
                builder.setMessage( "您确定要购买么？" );
                builder.setPositiveButton( "确定购买", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FoodT foodT = mFoodList.get(position);
                        String food_name = foodT.getNameF();
                        String food_price= foodT.getPrice();
                        int food_photo=foodT.getImageIdF();
                        dbHelper.getWritableDatabase();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("food_name", food_name);
                        values.put("food_price",food_price);
                        values.put("food_photo", food_photo);
                        values.put("shop_name", abc);
                        values.put("account", TaccountF);
                        db.insert("Menu", null, values);
                        values.clear();

                        Toast.makeText(Main7Activity.this,"购买成功，欢迎下次光临~",Toast.LENGTH_SHORT).show();
                    }
                } );

                builder.setNegativeButton( "我再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } );
                AlertDialog alert = builder.create();
                alert.show();




            }
        } );
//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        btn_AddFOOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCompareAccount(TaccountF,abc)) {
                    Toast.makeText(Main7Activity.this, "这不是你的店铺哦~", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Main7Activity.this,Main10Activity.class);
                    intent.putExtra("foodid", abc);
                    intent.putExtra("TaccountF",adc);
                    startActivity(intent);
                }
            }
        });

        btn_BAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main7Activity.this,Main3Activity.class);
                intent.putExtra("TaccountF",adc);
                startActivity(intent);
                Main7Activity.this.finish();
            }
        });

        btn_ShanchuFOOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCompareAccount(TaccountF,abc)) {
                    Toast.makeText(Main7Activity.this, "这不是你的店铺哦~", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Main7Activity.this, Main12Activity.class);
                    intent.putExtra("shop_name", abc);
                    intent.putExtra("TaccountF", adc);
                    //intent.putExtra("shop_name")
                    startActivity(intent);
                }
            }
        });

        btn_GenxinFOOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCompareAccount(TaccountF,abc)) {
                    Toast.makeText(Main7Activity.this, "这不是你的店铺哦~", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Main7Activity.this, Main13Activity.class);
                    intent.putExtra("shop_name", abc);
                    intent.putExtra("TaccountF", adc);
                    //intent.putExtra("shop_name")
                    startActivity(intent);
                }
            }
        });

    }
    private void initData() {
        Intent intent = getIntent();
        final String accountFrom37 = intent.getStringExtra("shop_Name37");
        //Toast.makeText(Main7Activity.this,accountFrom37,Toast.LENGTH_SHORT).show();
        final TextView tv_ts7=(TextView)findViewById(R.id.tv_ts7);
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Food", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String food_name = cursor.getString(cursor.getColumnIndex("food_name"));
                String food_price = cursor.getString(cursor.getColumnIndex("food_price"));
                String checkshop_position = cursor.getString(cursor.getColumnIndex("shop_name"));
               if(checkshop_position.equals(accountFrom37)){
                    FoodT foodT= new FoodT();
                    tv_ts7.setText("欢迎使用美团~");
                    foodT.setNameF(food_name);
                    foodT.setPrice(food_price);
                    foodT.setImageIdF(R.drawable.buweilogo2);
                    foodT.setDM("点此购买");
                    mFoodList.add(foodT);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();     //用过之后记得调用cursor的close函数
        }

    //判断这是不是你的店铺（不要妄想修改别人店铺的东西
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
}


