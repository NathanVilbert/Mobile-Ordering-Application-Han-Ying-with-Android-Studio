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

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private ArrayList<FoodDomain> foodDomains;

    public PopularAdapter(ArrayList<FoodDomain> FoodDomains) {
        this.foodDomains = FoodDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomain popular = foodDomains.get(position);

        holder.popularName.setText(popular.getName());
        holder.popularPrice.setText(String.valueOf(popular.getPrice()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popular.getImage(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.popularPic);

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
        TextView popularName, popularPrice;
        ImageView popularPic;
        TextView addBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popularName = itemView.findViewById(R.id.titleTxt2);
            popularPrice = itemView.findViewById(R.id.priceTxt2);
            popularPic = itemView.findViewById(R.id.foodPic2);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
