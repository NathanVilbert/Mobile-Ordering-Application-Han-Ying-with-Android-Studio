package com.example.hanying.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hanying.R;
import com.example.hanying.Domain.ProductDomain;

import java.util.List;
public class ProductAdapter extends ArrayAdapter<ProductDomain> {

    Context context;
    List<ProductDomain> arraylistproduct;

    public ProductAdapter(@NonNull Context context, List<ProductDomain> arraylistproduct) {
        super(context, R.layout.list_item, arraylistproduct);

        this.context = context;
        this.arraylistproduct =arraylistproduct;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null,true);

        TextView tmptTulisanID = view.findViewById(R.id.edtproductID);
        TextView tmptTulisanProduct = view.findViewById(R.id.edtproductName);

        tmptTulisanID.setText(arraylistproduct.get(position).getProductID());
        tmptTulisanProduct.setText(arraylistproduct.get(position).getProductName());

        return view;
    }
}
