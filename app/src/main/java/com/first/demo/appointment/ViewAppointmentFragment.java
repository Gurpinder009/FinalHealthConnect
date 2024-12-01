package com.first.demo.appointment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.first.demo.DbHelperClasses.AppointmentDbHelper;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.R;
import com.first.demo.models.Appointment;
import com.first.demo.utilities.FragmentHelper;


public class ViewAppointmentFragment extends Fragment implements FragmentHelper {




    TextView patientName;
    TextView appointmentDate;
    TextView appointmentTime;
    TextView appointmentReason;
    Appointment appointment;
    Button cancelAppointment;

    public ViewAppointmentFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_appointment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);

        initializeRequiredData();
        setLayoutListeners();




    }


    public void cancelAppointment(View view){
        DatabaseHelper db=DatabaseHelper.getInstance(getContext());
        AppointmentDbHelper.cancelAppointment(db,appointment.getId());
        toViewAppointments();
    }


    public void toViewAppointments(){
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack();
        Toast.makeText(getActivity(),
                "Appointment is cancelled", Toast.LENGTH_LONG).show();

    }



    @Override
    public void findAndAssignLayoutElements(View view) {
        patientName = view.findViewById(R.id.patientName);
        appointmentDate = view.findViewById(R.id.appointmentDate);
        appointmentTime = view.findViewById(R.id.appointmentTime);
        appointmentReason = view.findViewById(R.id.appointmentReason);
        cancelAppointment = view.findViewById(R.id.cancelAppointmentBtn);
    }

    @Override
    public void setLayoutListeners() {
        cancelAppointment.setOnClickListener(this::cancelAppointment);

    }

    @Override
    public void initializeRequiredData() {
        assert getArguments() != null;
        String value = getArguments().getString("id");
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        assert value != null;
        appointment = AppointmentDbHelper.query(db,Integer.parseInt(value));
        appointment.setId(Integer.parseInt(value));
        patientName.setText( "Name: " + appointment.getPatientName());
        appointmentDate.setText("Date: "+appointment.getDate());
        appointmentReason.setText("Reason:  "+appointment.getReason());
        appointmentTime.setText("Time: "+appointment.getTime());


    }
}