package com.example.hanying.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hanying.Activity.ShowDetailActivity;
import com.example.hanying.Domain.FoodDomain;
import com.example.hanying.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    ArrayList<FoodDomain> foodDomains;

    public MenuAdapter(ArrayList<FoodDomain> FoodDomains) {
        this.foodDomains = FoodDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_menu, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(foodDomains.get(position).getName());
        holder.price.setText(String.valueOf(foodDomains.get(position).getPrice()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getImage(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.productImage);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", foodDomains.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, price;
        ImageView productImage;
        TextView addBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.price);
            productImage = itemView.findViewById(R.id.productImage);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
