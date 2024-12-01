package com.first.demo.patient;

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
import com.first.demo.DbHelperClasses.ConsultationDbHelper;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.DbHelperClasses.MedicationTrackingDbHelper;
import com.first.demo.DbHelperClasses.PatientDbHelper;
import com.first.demo.R;
import com.first.demo.adaptors.PatientRecycleViewAdaptor;
import com.first.demo.models.MedicationTracking;
import com.first.demo.models.Patient;
import com.first.demo.utilities.FragmentHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ViewPatientsFragment extends Fragment implements FragmentHelper {

    // layout members
    FloatingActionButton addPatientBtn;
    RecyclerView patientRecycleView;
    EditText searchPatientInput;

    //patient list
    ArrayList<Patient> patients;

    //for recyclerView
    PatientRecycleViewAdaptor adaptor;

    public ViewPatientsFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_patients, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);
        setLayoutListeners();
        initializeRequiredData();
        initializeRecyclerView();
        onSearchPatients();

    }




    //initializing patient recyclerView
    public void initializeRecyclerView() {
        patientRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptor = new PatientRecycleViewAdaptor(getContext(), patients, this::toUpdatePatient);
        patientRecycleView.setAdapter(adaptor);

    }


    //binding view with instance members
    @Override
    public void findAndAssignLayoutElements(View view){
        addPatientBtn = view.findViewById(R.id.addPatientBtn);
        searchPatientInput = view.findViewById(R.id.searchInput);
        patientRecycleView = view.findViewById(R.id.patientRecyclerView);
    }

    //setting listeners on layout elements
    @Override
    public void setLayoutListeners(){
        addPatientBtn.setOnClickListener(this::toAddPatient);

    }

    @Override
    public void initializeRequiredData() {
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());

        patients = PatientDbHelper.queryAll(db);
        if(patients.isEmpty()){
        PatientDbHelper.seedPatients(db);
        AppointmentDbHelper.seedAppointments(db);
        ConsultationDbHelper.seedConsultation(db);
//            MedicationTrackingDbHelper
        }
    }


    //searching logic
    public void onSearchPatients(){
        searchPatientInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = searchPatientInput.getText().toString();
                patients.clear();
                DatabaseHelper db = DatabaseHelper.getInstance(getContext());
                patients.addAll(PatientDbHelper.queryAllWithLike(db, input));
                adaptor.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }


    //moving to addPatient Fragment
    public void toAddPatient(View view) {
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction().setReorderingAllowed(true).addToBackStack("name").replace(R.id.frameLayout, new AddPatientFragment()).commit();
    }


    //moving to updatePatient Fragment
    public void toUpdatePatient(int position){
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment newFrag = new ViewPatientFragment();
        Patient p = patients.get(position);
        Bundle args = new Bundle();
        args.putString("id", String.valueOf(p.getId()));
        newFrag.setArguments(args);
        ft.replace(R.id.frameLayout, newFrag);
        ft.setReorderingAllowed(true);
        ft.addToBackStack("name");
        ft.commit();
    }


}