package com.first.demo.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.first.demo.appointment.AddAppointmentFragment;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.DbHelperClasses.PatientDbHelper;
import com.first.demo.R;
import com.first.demo.consultations.AddConsultationFragment;
import com.first.demo.models.Patient;
import com.first.demo.utilities.FragmentHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ViewPatientFragment extends Fragment implements FragmentHelper {


    //Patient Data
    Patient patient;

    //layout elements
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView phone;
    TextView gender;
    TextView emergencyContact;
    TextView address;
    TextView dob;
    TextView bloodType;
    TextView height;
    TextView weight;
    TextView allergies;
    Button deletePatientBtn;
    FloatingActionButton updatePatientBtn;
    Button addAppointmentBtn;
    Button addConsultationBtn;






    public ViewPatientFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_patient, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);
        setLayoutListeners();
        initializeRequiredData();
        updatePatientDetails();
    }


    public void initializeRequiredData(){
        assert getArguments() != null;
        String value = getArguments().getString("id");
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        assert value != null;
        patient = PatientDbHelper.query(db,Integer.parseInt(value));
    }


    public void updatePatient(View view){

        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment newFrag = new UpdatePatientFragment();
        Bundle args = new Bundle();
        args.putString("id",String.valueOf(patient.getId()));
        newFrag.setArguments(args);
        ft.replace(R.id.frameLayout,newFrag);
        ft.addToBackStack("name");
        ft.setReorderingAllowed(true);
        ft.commit();

    }


    public void deletePatient(View view){
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        PatientDbHelper.delete(db,patient.getId());
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack();
        Toast.makeText(getContext(),"Patient is removed",Toast.LENGTH_LONG).show();

    }


    public void toAddAppointment(View view){
        FragmentManager fm = getParentFragmentManager();
        Bundle bundle = new Bundle();
        AddAppointmentFragment frag = new AddAppointmentFragment();
        bundle.putString("patient_name", patient.getFirstName()+" "+ patient.getLastName());
        bundle.putString("patient_id", String.valueOf(patient.getId()));
        frag.setArguments(bundle);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, frag);
        ft.addToBackStack("name");
        ft.setReorderingAllowed(true);
        ft.commit();
    }


    public void toAddConsultation(View view){
        FragmentManager fm = getParentFragmentManager();
        Bundle bundle = new Bundle();
        AddConsultationFragment frag = new AddConsultationFragment();
        bundle.putString("patient_id", String.valueOf(patient.getId()));
        frag.setArguments(bundle);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, frag);
        ft.addToBackStack("name");
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    public void updatePatientDetails(){


        firstName.setText("First Name: "+patient.getFirstName());
        lastName.setText("Last Name: "+patient.getLastName());
        email.setText("Email: "+patient.getEmailAddress());
        phone.setText("Phone: "+patient.getPhoneNumber());
        gender.setText("Gender: "+patient.getGender());
        dob.setText("Date of birth: "+patient.getDob().toString());
        address.setText("Address: "+patient.getAddress());
        emergencyContact.setText("Emergency Contact: "+patient.getEmergencyContact());
        bloodType.setText("Blood Type: "+patient.getBloodType());
        height.setText("height: "+patient.getHeight());
        weight.setText("Blood Type: "+patient.getWeight());
        allergies.setText("Blood Type: "+patient.getAllergies());
    }

    @Override
    public void findAndAssignLayoutElements(View view) {
        firstName= view.findViewById(R.id.firstName);
        lastName= view.findViewById(R.id.lastName);
        email= view.findViewById(R.id.email);
        phone= view.findViewById(R.id.phone);
        dob= view.findViewById(R.id.dob);
        gender= view.findViewById(R.id.gender);
        address= view.findViewById(R.id.address);
        emergencyContact= view.findViewById(R.id.emergencyContact);
        bloodType= view.findViewById(R.id.bloodType);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        allergies = view.findViewById(R.id.allergies);
        addAppointmentBtn = view.findViewById(R.id.addApptBtn);
        deletePatientBtn = view.findViewById(R.id.removePatientBtn);
        updatePatientBtn = view.findViewById(R.id.udpatePatientBtn);
        addConsultationBtn = view.findViewById(R.id.addConsultationBtn);
    }

    @Override
    public void setLayoutListeners() {

        updatePatientBtn.setOnClickListener(this::updatePatient);
        deletePatientBtn.setOnClickListener(this::deletePatient);
        addAppointmentBtn.setOnClickListener(this::toAddAppointment);
        addConsultationBtn.setOnClickListener(this::toAddConsultation);

    }
}




