package com.first.demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.DbHelperClasses.MedicationTrackingDbHelper;
import com.first.demo.models.MedicationTracking;
import com.first.demo.patient.ViewPatientsFragment;
import com.first.demo.utilities.DataValidator;
import com.first.demo.utilities.FragmentHelper;

public class AddMedicationFragment extends Fragment implements FragmentHelper {

    EditText medicationInput;
    EditText medicationDosage;
    EditText medicationDuration;
    Button addMedicationBtn;
    int consultationId;


    Button addMoreMedicationBtn;



    public AddMedicationFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_medication, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);
        setLayoutListeners();
        initializeRequiredData();
    }

    @Override
    public void findAndAssignLayoutElements(View view) {
        medicationInput = view.findViewById(R.id.medicationTitleInput);
        medicationDosage = view.findViewById(R.id.medicationDosageInput);
        medicationDuration = view.findViewById(R.id.medicationDurationInput);
        addMedicationBtn = view.findViewById(R.id.addMedicationBtn);
        addMoreMedicationBtn = view.findViewById(R.id.addMoreMedicationBtn);
    }



    public void processData(){
        medicationInput.setText(medicationInput.getText().toString().trim());
        medicationDosage.setText(medicationDosage.getText().toString().trim());
        medicationDuration.setText(medicationDuration.getText().toString().trim());

    }



    public boolean saveMedication(View view){
        processData();
        String medTitle = medicationInput.getText().toString();
        String medDosage = medicationDosage.getText().toString();
        String medDuration = medicationDuration.getText().toString();


        if(DataValidator.validateText(getContext(),medTitle) && DataValidator.validateText(getContext(),medDosage) && DataValidator.validateText(getContext(), medDuration)){
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        MedicationTrackingDbHelper.add(db, new MedicationTracking(medTitle,medDosage,medDuration,consultationId));
        toPatients();
        return true;

        }
        return false;


    }


    public void toPatients(){
        FragmentManager fragmentManager = getParentFragmentManager();
        ViewPatientsFragment patientsFragment = new ViewPatientsFragment();
//        fragmentManager.clearBackStack("name");
        FragmentTransaction fragmentTrans = fragmentManager.beginTransaction();
        fragmentTrans.replace(R.id.frameLayout, patientsFragment);
        fragmentTrans.commit();
    }



    public void addMoreMedication(View view){
        if(saveMedication(view)){
            FragmentManager fragmentManager = getParentFragmentManager();
            AddMedicationFragment addMedication = new AddMedicationFragment();
            Bundle b = new Bundle();
            b.putLong("consultation_id", consultationId);
            addMedication.setArguments(b);
            FragmentTransaction fragmentTrans = fragmentManager.beginTransaction();
            fragmentTrans.replace(R.id.frameLayout, addMedication);
            fragmentTrans.addToBackStack("name");
            fragmentTrans.setReorderingAllowed(true);
            fragmentTrans.commit();
        }
    }




    @Override
    public void setLayoutListeners() {

        addMedicationBtn.setOnClickListener(this::saveMedication);
        addMoreMedicationBtn.setOnClickListener(this::addMoreMedication);

    }

    @Override
    public void initializeRequiredData() {
        assert getArguments() != null;
        consultationId = (int) getArguments().getLong("consultation_id");
    }




}