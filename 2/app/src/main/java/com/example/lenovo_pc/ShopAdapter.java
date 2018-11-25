package com.example.lenovo_pc;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private static OnItemClickListener mOnItemClickListener;




    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public static void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }



    private List<Shop> list;

    static class ViewHolder extends RecyclerView.ViewHolder {



        //绑定点击事件!!!!!!!!!!!!
        //View shopView;
        //绑定点击事件!!!!!!!!!!
        ImageView shopImage;
        TextView shopName;
        TextView shopLocation;
        TextView shopusername;
        View shopView;             //这个view指的是自身（这一整个item）

        public ViewHolder(View view) {
            super(view);
            shopView = view;
            shopImage = (ImageView) view.findViewById(R.id.iv_shop);
            shopName = (TextView) view.findViewById(R.id.tv_name);
            shopLocation = (TextView) view.findViewById(R.id.tv_location);
            shopusername = (TextView) view.findViewById(R.id.tv_ZH);
        }
    }

    public ShopAdapter(List<Shop> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_shop, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.shopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Shop shop = list.get(position);
                Toast.makeText(view.getContext(), "Sorry,本APP暂时没有设置修改图片功能~", Toast.LENGTH_SHORT).show();
            }
        });


       holder.shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
               Shop shop = list.get(position);
                //Toast.makeText(view.getContext(), shop.getLocation(),Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(view.getContext(), Main7Activity.class);
               // intent.putExtra("shop_Name",shop.getName());
                //intent.putExtra("shop_Name37",shop.getName());
                //Toast.makeText(view.getContext(), shop.getName(),Toast.LENGTH_SHORT).show();
                mOnItemClickListener.onItemClick(v, position);
            }
        });

        holder.shopLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //Shop shop = list.get(position);
               // Intent intent = new Intent(view.getContext(), Main7Activity.class);
               // intent.putExtra("shop_Nameas",shop.getName());
                mOnItemClickListener.onItemClick(v, position);

            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Shop shop = list.get(i);
        viewHolder.shopImage.setImageResource(shop.getImageId());
        viewHolder.shopName.setText(String.valueOf(shop.getName()));
        viewHolder.shopusername.setText(shop.getZH());
        viewHolder.shopLocation.setText(String.valueOf(shop.getLocation()));
    }




    @Override
    public int getItemCount() {

        return list.size();
    }

}
