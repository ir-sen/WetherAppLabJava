package com.example.wetherapplabjava;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<CitysTemp> citys;

    public RecycleAdapter(LayoutInflater inflater, List cityT, java.util.List<CitysTemp> citys) {
        this.inflater = inflater;
        this.citys = citys;
    }

    RecycleAdapter(Context context, List<CitysTemp> citys) {
        this.citys = citys;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        CitysTemp city = citys.get(position);
        holder.cityTv.setText(city.getCity());
        holder.tempTv.setText(city.getTemp());


    }

    @Override
    public int getItemCount() {
        return citys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView cityTv;
        final TextView tempTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTv = itemView.findViewById(R.id.cityTv);
            tempTv = itemView.findViewById(R.id.tempTv);
        }
    }
}
