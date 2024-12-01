package com.first.demo.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.first.demo.R;
import com.first.demo.models.Consultation;
import com.first.demo.utilities.OnItemClickListener;

import java.util.ArrayList;

public class ConsultationRecycleViewAdaptor  extends RecyclerView.Adapter <ConsultationRecycleViewAdaptor.ViewHolder>{
    private OnItemClickListener listener;
    Context context;
    ArrayList<Consultation> consultations;
    public ConsultationRecycleViewAdaptor(Context context, ArrayList<Consultation> consultations, OnItemClickListener listener){
        this.context = context;
        this.consultations = consultations;
        this.listener =listener;

    }


    @NonNull
    @Override
    public ConsultationRecycleViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup consultations, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.consultation_list, consultations, false);
        return new ConsultationRecycleViewAdaptor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultationRecycleViewAdaptor.ViewHolder holder, int position) {
        holder.textView1.setText(consultations.get(position).getPatientName());
        String idString = "Date: "+consultations.get(position).getDate();
        holder.textView2.setText(idString);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return consultations.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView textView1, textView2;


        public ViewHolder(View itemView){
            super(itemView);
            textView1 = itemView.findViewById(R.id.name);
            textView2 = itemView.findViewById(R.id.id);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

    }


}