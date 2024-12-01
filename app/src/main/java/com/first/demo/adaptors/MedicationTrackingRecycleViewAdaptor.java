package com.first.demo.adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.first.demo.R;
import com.first.demo.models.MedicationTracking;
import com.first.demo.utilities.OnItemClickListener;

import java.util.ArrayList;

public class MedicationTrackingRecycleViewAdaptor  extends RecyclerView.Adapter <MedicationTrackingRecycleViewAdaptor.ViewHolder>{
    private OnItemClickListener listener;
    Context context;
    ArrayList<MedicationTracking> medicationTrackings;
    public MedicationTrackingRecycleViewAdaptor(Context context, ArrayList<MedicationTracking> medicationTrackings){
        this.context = context;
        this.medicationTrackings = medicationTrackings;

    }


    @NonNull
    @Override
    public MedicationTrackingRecycleViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup medicationTrackings, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.medication_tracking_list, medicationTrackings, false);
        return new MedicationTrackingRecycleViewAdaptor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationTrackingRecycleViewAdaptor.ViewHolder holder, int position) {
        String medicationNameString = "Medication Name: " +medicationTrackings.get(position).getMedication();
        holder.textView1.setText(medicationNameString);
        String medicationDosageString = "Dosage: "+medicationTrackings.get(position).getDosage();
        holder.textView2.setText(medicationDosageString);
        String medicationDuration = "Duration"+medicationTrackings.get(position).getDuration();

        holder.textView3.setText(medicationDuration);


    }

    @Override
    public int getItemCount() {
        return medicationTrackings.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView textView1, textView2,textView3;


        public ViewHolder(View itemView){
            super(itemView);
            textView1 = itemView.findViewById(R.id.medicationName);
            textView2 = itemView.findViewById(R.id.medicationDosage);
            textView3 = itemView.findViewById(R.id.medicationDuration);

        }

    }


}