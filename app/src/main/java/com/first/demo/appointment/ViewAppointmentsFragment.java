package com.first.demo.appointment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.first.demo.DbHelperClasses.AppointmentDbHelper;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.R;
import com.first.demo.adaptors.AppointmentRecycleViewAdaptor;
import com.first.demo.models.Appointment;
import com.first.demo.utilities.FragmentHelper;

import java.util.ArrayList;


public class ViewAppointmentsFragment extends Fragment implements FragmentHelper {


    RecyclerView appointmentRecycleView;
    ArrayList<Appointment> appointments;
//    EditText searchAppointment;

    public ViewAppointmentsFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointments, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);
        initializeRequiredData();




        appointmentRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));



        AppointmentRecycleViewAdaptor adaptor = new AppointmentRecycleViewAdaptor(getContext(), appointments, position -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment newFrag = new ViewAppointmentFragment();
            Appointment a = appointments.get(position);
            Bundle args = new Bundle();

            args.putString("id",String.valueOf(a.getId()));
            newFrag.setArguments(args);
            ft.replace(R.id.frameLayout,newFrag);
            ft.setReorderingAllowed(true);
            ft.addToBackStack("name");
            ft.commit();
        });
        appointmentRecycleView.setAdapter(adaptor);




    }

    @Override
    public void findAndAssignLayoutElements(View view) {
//        searchAppointment = view.findViewById(R.id.searchApptInput);
        appointmentRecycleView= view.findViewById(R.id.appointmentRecyclerView);


    }

    @Override
    public void setLayoutListeners() {

    }

    @Override
    public void initializeRequiredData() {
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
//        AppointmentDbHelper.seedAppointments(db);
        appointments = AppointmentDbHelper.queryAll(db);

    }
}

















//
////search app by pateint name
//        searchAppointment.addTextChangedListener(new TextWatcher() {
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        String input = searchAppointment.getText().toString();
//        appointments.clear();
//        appointments.addAll(AppointmentDbHelper.queryAllWithLike(DatabaseHelper.getInstance(getContext()),input));
//        adaptor.notifyDataSetChanged();
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//
//    }
//});