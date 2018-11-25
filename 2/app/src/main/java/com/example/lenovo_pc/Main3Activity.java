package com.example.lenovo_pc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {


    private MyDatabaseHelper dbHelper;
    private List<Shop> mShopList = new ArrayList<>();


//实现在5活动中关闭3活动
    public static Main3Activity instance;

    static int i = 0;
    static String[] name = new String[1000];
    static String[] location = new String[1000];
    static String[] A = new String[1000];
    static int[] imageId = new int[1000];
    private static String abc;
    //做一个对外的接口
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    //做一个对外的接口


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        instance = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //在菜单界面显示用户名
        //将登录账号导入在该界面(接收)
        final Intent intent = getIntent();
        final String accountFrom = intent.getStringExtra("account1");
        final String accountFrom8 = intent.getStringExtra("account83");
        final String accountFrom73=intent.getStringExtra("TaccountF");
        final TextView tv_ts3=(TextView)findViewById(R.id.tv_ts3);
        tv_ts3.setText("这里暂时没有店铺哦~");
//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        initData();
        //实现了跳转时保留店主！！！！！！！！！！！！！！！！！！！！！！！！！！
        if(accountFrom!= null)abc = accountFrom;
        if(abc == null && accountFrom8!= null)abc = accountFrom8;
        if(abc==null) abc=accountFrom73;
        //实现了跳转时保留店主！！！！！！！！！！！！！！！！！！！！！！！！！！
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);





        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ShopAdapter shopAdapter = new ShopAdapter(mShopList);
        recyclerView.setAdapter(shopAdapter);


       ShopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //实现导入item中的数据
                Shop shop = mShopList.get(position);
                Intent intent = new Intent(view.getContext(), Main7Activity.class);
                intent.putExtra("shop_Name37",shop.getName());
                intent.putExtra("account_37",abc);
                Toast.makeText(Main3Activity.this,shop.getName(),Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } );

//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                //跳转到添加店铺
                //将账号导入到数据库中！！！！！！！！！！！！！！！！！！！！！！
                Intent intent = new Intent(Main3Activity.this,Main5Activity.class);
                intent.putExtra("account1", abc);
                startActivity(intent);
                //Main3Activity.this.finish();
                //
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //在个人界面显示账号
        TextView tv_username = (TextView)findViewById(R.id.tv_username);
        tv_username.setText(abc);
        //在个人界面显示账号
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(Main3Activity.this,Main8Activity.class);
            intent.putExtra("account8", abc);
            startActivity(intent);
            Main3Activity.this.finish();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(Main3Activity.this,Main11Activity.class);
            intent.putExtra("account8", abc);
            startActivity(intent);
            Main3Activity.this.finish();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(Main3Activity.this, "您的资产为9e9元\n赶快去买点儿东西吧~", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(Main3Activity.this, "Sorry，本APP暂时没有开通购物车功能~", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_register) {
            Intent intent = new Intent(Main3Activity.this,Main2Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_leave) {
            Intent intent = new Intent(Main3Activity.this,MainActivity.class);
            startActivity(intent);
            if(MainActivity.instance != null){
                MainActivity.instance.finish();
            }
            Main3Activity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void initData() {
        final TextView tv_ts3=(TextView)findViewById(R.id.tv_ts3);
        dbHelper = new MyDatabaseHelper(this, "Store.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Shop", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String shop_name1 = cursor.getString(cursor.getColumnIndex("shop_name"));
                String shop_location = cursor.getString(cursor.getColumnIndex("shop_location"));
                String u_a = cursor.getString(cursor.getColumnIndex("account"));
                Shop shop = new Shop();

                tv_ts3.setText("欢迎使用美团~");

                shop.setName(shop_name1);
                shop.setLocation(shop_location);
                shop.setImageId(R.drawable.buweilogo1);
                shop.setZH(u_a);
                mShopList.add(shop);

            } while (cursor.moveToNext());
        }
        cursor.close();     //用过之后记得调用cursor的close函数
    }














}
