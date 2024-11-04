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
import com.example.hanying.Domain.UserDomain;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserDomain> {

    Context context;
    List<UserDomain> arraylistuser;

    public UserAdapter(@NonNull Context context, List<UserDomain> arraylistuser) {
        super(context, R.layout.list_user, arraylistuser);

        this.context = context;
        this.arraylistuser = arraylistuser;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, null, true);

        TextView tmptTulisanID = view.findViewById(R.id.text);
        TextView tmptTulisanuser = view.findViewById(R.id.edtcustname);

        tmptTulisanID.setText(arraylistuser.get(position).getUsername());
        tmptTulisanuser.setText(arraylistuser.get(position).getCustname());

        return view;
    }
}
