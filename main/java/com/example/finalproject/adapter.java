package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.viewholder> {

    Context context;
    ArrayList<itemsapi> sapi;


    public adapter(Context c, ArrayList<itemsapi> s){
        context = c;
        sapi = s;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.item_sapi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
        holder.title.setText(sapi.get(position).getTitle());
        holder.sex.setText(sapi.get(position).getSex());

        final String getTitle = sapi.get(position).getTitle();
        final String getSex = sapi.get(position).getSex();
        final String getSapiKey = sapi.get(position).getSapikey();


        /*holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(context, Tanggal.class);
                context.startActivity(i);
                return true;
            }
        });*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, read.class);
                i.putExtra("weight", sapi.get(position).getWeight());
                i.putExtra("title", getTitle);
                i.putExtra("sex", getSex);
                i.putExtra("breed", sapi.get(position).getBreed());
                i.putExtra("date", sapi.get(position).getDate());
                i.putExtra("place", sapi.get(position).getPlace());
                i.putExtra("sapikey", getSapiKey);
                context.startActivity(i);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, edit.class);
                i.putExtra("weight", sapi.get(position).getWeight());
                i.putExtra("title", getTitle);
                i.putExtra("sex", getSex);
                i.putExtra("breed", sapi.get(position).getBreed());
                i.putExtra("date", sapi.get(position).getDate());
                i.putExtra("place", sapi.get(position).getPlace());
                i.putExtra("sapikey", getSapiKey);
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {

        return sapi.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView title, sex, keysapi;
        ImageButton edit;

        public viewholder(@NonNull View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.judul);
            sex = (TextView) itemView.findViewById(R.id.jenisks);
            edit = (ImageButton) itemView.findViewById(R.id.update);

        }


    }


}
