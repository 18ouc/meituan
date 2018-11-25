package com.example.lenovo_pc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FoodAdapterT extends RecyclerView.Adapter<FoodAdapterT.ViewHolder> {
    private static FoodAdapterT.OnItemClickListener mOnItemClickListener;




    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public static void setOnItemClickListener(FoodAdapterT.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = (FoodAdapterT.OnItemClickListener) onItemClickListener;
    }



    private List<FoodT> list;

    static class ViewHolder extends RecyclerView.ViewHolder {



        //绑定点击事件!!!!!!!!!!!!
        //View shopView;
        //绑定点击事件!!!!!!!!!!
        ImageView foodImage;
        TextView foodName;
        TextView foodPrice;
        TextView foodDM;
        View foodView;             //这个view指的是自身（这一整个item）

        public ViewHolder(View view) {
            super(view);
            foodView = view;
            foodImage= (ImageView) view.findViewById(R.id.iv_food);
            foodName = (TextView) view.findViewById(R.id.tv_namef);
            foodPrice = (TextView) view.findViewById(R.id.tv_pricef);
            foodDM = (TextView) view.findViewById(R.id.tv_buy);
        }
    }

    public FoodAdapterT(List<FoodT> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FoodAdapterT.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_food, viewGroup, false);
        final FoodAdapterT.ViewHolder holder = new FoodAdapterT.ViewHolder(view);
       holder.foodDM.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int position = holder.getAdapterPosition();
               FoodT foodT = list.get(position);
               Toast.makeText(view.getContext(), foodT.getNameF(),Toast.LENGTH_SHORT).show();
               mOnItemClickListener.onItemClick(v, position);
           }
       });
       holder.foodImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int position = holder.getAdapterPosition();
               FoodT foodT = list.get(position);
               Toast.makeText(view.getContext(), "Sorry,本APP暂时没有设置修改图片功能~",Toast.LENGTH_SHORT).show();
           }
       });
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull FoodAdapterT.ViewHolder viewHolder, int i) {
        FoodT foodT= list.get(i);
        viewHolder.foodImage.setImageResource(foodT.getImageIdF());
        viewHolder.foodName.setText(foodT.getNameF());
        viewHolder.foodDM.setText(foodT.getDM());
        viewHolder.foodPrice.setText(String.valueOf(foodT.getPrice()));
    }




    @Override
    public int getItemCount() {
        return list.size();
    }
}
