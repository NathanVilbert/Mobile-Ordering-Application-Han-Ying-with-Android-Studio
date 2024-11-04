package com.example.hanying.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.hanying.Domain.TrendsDomain;
import com.example.hanying.R;

import java.util.ArrayList;

public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<TrendsDomain> trendItemList;
    buttonClickListener buttonClickListener;

    public TrendsAdapter(Context context, ArrayList<TrendsDomain> trendItemList, buttonClickListener buttonClickListener) {
        this.context = context;
        this.trendItemList = trendItemList;
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.viewholder_trends,parent,false);
        return new ViewHolder(view, buttonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrendsDomain trendItem = trendItemList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(trendItem.getImageResourceId())
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);

        holder.title.setText(trendItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return trendItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView pic;

        Button button;
        buttonClickListener buttonClickListener;

        public ViewHolder(@NonNull View itemView, buttonClickListener buttonClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.foodPic2);
            button = itemView.findViewById(R.id.button);

            this.buttonClickListener = buttonClickListener;
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            buttonClickListener.onButtonClick(getAdapterPosition());

        }
    }

    public interface buttonClickListener{ void onButtonClick (int position);

    }
}
