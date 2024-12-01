package com.first.demo.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.first.demo.utilities.OnItemClickListener;
import com.first.demo.R;
import com.first.demo.models.Appointment;

import java.util.ArrayList;

public class AppointmentRecycleViewAdaptor extends RecyclerView.Adapter <AppointmentRecycleViewAdaptor.ViewHolder>{
    private OnItemClickListener listener;
    Context context;
    ArrayList<Appointment> appointments;
    public AppointmentRecycleViewAdaptor(Context context, ArrayList<Appointment> appointment,OnItemClickListener listener){
        this.context = context;
        this.appointments = appointment;
        this.listener =listener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup appointments, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.appointment_list, appointments, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView1.setText(appointments.get(position).getPatientName());
        String idString = "Date: "+String.valueOf(appointments.get(position).getDate());
        holder.textView2.setText(idString);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
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