package com.first.demo.consultations;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.first.demo.AddMedicationFragment;
import com.first.demo.DbHelperClasses.ConsultationDbHelper;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.R;
import com.first.demo.models.Consultation;
import com.first.demo.utilities.DataValidator;
import com.first.demo.utilities.FragmentHelper;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddConsultationFragment extends Fragment implements FragmentHelper {
    TextView consultationDateInput;
    EditText consultationDiagnosesInput;
    EditText consultationTreatmentInput;
    EditText symptomsInput;
    Button addConsultationBtn;
    Button addMedicationBtn;
    int patientId;
    long consultation_id;
    int year;
    int month;
    int day;



    ImageButton dateBtnP;
    Button registerButton;
    DatePickerDialog datePickerDialog;

    public AddConsultationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_consultation, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);
        initializeRequiredData();
        setLayoutListeners();

    }

    @Override
    public void findAndAssignLayoutElements(View view) {
        consultationDateInput = view.findViewById(R.id.consultationDateInput);
        consultationDiagnosesInput = view.findViewById(R.id.consultationDiagnosesInput);
        consultationTreatmentInput = view.findViewById(R.id.consultationTreatmentInput);
        symptomsInput = view.findViewById(R.id.consultationsSymptomsInput);
        addConsultationBtn = view.findViewById(R.id.addConsultationBtn);
        addMedicationBtn = view.findViewById(R.id.addMedicationBtn);
        dateBtnP = view.findViewById(R.id.dateBtnP);

    }



    public void addConsultation(View view){
        if(saveConsultation()){
            toAddPatient(view);
        }

    }



    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);

                        consultationDateInput.setText(selectedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }


    public void proccessData(){
        consultationDateInput.setText(consultationDateInput.getText().toString().trim());
        symptomsInput.setText(symptomsInput.getText().toString().trim());
        consultationDiagnosesInput.setText(consultationDiagnosesInput.getText().toString().trim());
        consultationTreatmentInput.setText(consultationTreatmentInput.getText().toString().trim());

    }


    public boolean saveConsultation(){
        proccessData();
        String date = consultationDateInput.getText().toString();
        String  symptoms = symptomsInput.getText().toString();
        String treatment = consultationTreatmentInput.getText().toString();
        String diagnoses = consultationDiagnosesInput.getText().toString();



        if(DataValidator.validateDate(getContext(),date) && DataValidator.validateText(getContext(),diagnoses) && DataValidator.validateText(getContext(),treatment) && DataValidator.validateText(getContext(), symptoms)){
            DatabaseHelper db = DatabaseHelper.getInstance(getContext());
            consultation_id=ConsultationDbHelper.add(db,new Consultation(date,symptoms,treatment,diagnoses,patientId));
            Toast.makeText(getContext(), "Consultation data is saved", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void toAddPatient(View view){
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();



    }
    public void toMedication(View view){
        if(saveConsultation()){


        FragmentManager fragmentManager = getParentFragmentManager();
        AddMedicationFragment addMedication = new AddMedicationFragment();
        Bundle b = new Bundle();
        b.putLong("consultation_id", consultation_id);
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
        addMedicationBtn.setOnClickListener(this::toMedication);
        addConsultationBtn.setOnClickListener(this::addConsultation);
        dateBtnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    @Override
    public void initializeRequiredData() {
        assert getArguments() != null;
        String patientIdString = getArguments().getString("patient_id");
        assert patientIdString != null;
        patientId = Integer.parseInt(patientIdString);


    }
}