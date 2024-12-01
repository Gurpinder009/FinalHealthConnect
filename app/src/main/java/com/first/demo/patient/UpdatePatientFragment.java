package com.first.demo.patient;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.DbHelperClasses.PatientDbHelper;
import com.first.demo.R;
import com.first.demo.models.Patient;
import com.first.demo.utilities.DataValidator;
import com.first.demo.utilities.FragmentHelper;

import java.util.Calendar;
import java.util.Locale;


public class UpdatePatientFragment extends Fragment implements FragmentHelper {
    int year;
    int month;
    int day;

    // patient information
    Patient patient;

    //layout elements
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText phoneInput;
//    DatePicker dobInput;
    EditText genderInput;
    EditText addressInput;
    EditText emergencyContactInput;
    EditText bloodTypeInput;
    EditText heightInput;
    EditText weightInput;
    EditText allergiesInput;
    Button updateBtn;
    TextView tvSelectedDateP;
    ImageButton dateBtnP;
    DatePickerDialog datePickerDialog;



    public UpdatePatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_patient, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       findAndAssignLayoutElements(view);
        setLayoutListeners();
        initializeRequiredData();
        populatingUpdateFields();
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
                        // Format the date
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);

                        // Update the TextView
                        tvSelectedDateP.setText(selectedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }


    public void processGivenData(){
        firstNameInput.setText( firstNameInput.getText().toString().trim());
        lastNameInput.setText( lastNameInput.getText().toString().trim());
        emailInput.setText( emailInput.getText().toString().trim());
        phoneInput.setText( phoneInput.getText().toString().trim());
        genderInput.setText( genderInput.getText().toString().trim());
        addressInput.setText( addressInput.getText().toString().trim());
        emergencyContactInput.setText( emergencyContactInput.getText().toString().trim());
        bloodTypeInput.setText( bloodTypeInput.getText().toString().trim());
        heightInput.setText( heightInput.getText().toString().trim());
        weightInput.setText( weightInput.getText().toString().trim());
        allergiesInput.setText( allergiesInput.getText().toString().trim());
        tvSelectedDateP.setText( tvSelectedDateP.getText().toString().trim());

    }

    public void saveUpdatedPatientDetails(View view){
        processGivenData();
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String gender = genderInput.getText().toString();
        String address = addressInput.getText().toString();
        String emergencyContact = emergencyContactInput.getText().toString();
        String bloodType = bloodTypeInput.getText().toString();
        String height = heightInput.getText().toString();
        String weight = weightInput.getText().toString();
        String allergies = allergiesInput.getText().toString();
        String dob = tvSelectedDateP.getText().toString();

        if(DataValidator.validateFirstName(getContext(),firstName) && DataValidator.validateLastName(getContext(),lastName) && DataValidator.validateEmail(getContext(),email) && DataValidator.validatePhoneNumber(getContext(),phone) && DataValidator.validateDOB(getContext(),dob) && DataValidator.validateGender(getContext(),gender)&& DataValidator.validateAddress(getContext(),address)&& DataValidator.validatePhoneNumber(getContext(),emergencyContact) && DataValidator.validateBloodType(getContext(), bloodType) && DataValidator.validateHeight(getContext(),height)&& DataValidator.validateWeight(getContext(),weight) ){

        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        PatientDbHelper.update(db,new Patient(patient.getId(),firstName,lastName,email,phone,dob,address,gender,emergencyContact,bloodType,height,weight,allergies,1));
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack();
        Toast.makeText(getContext(), "patient information updated", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void findAndAssignLayoutElements(View view) {
        firstNameInput = view.findViewById(R.id.firstNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        emailInput = view.findViewById(R.id.emailInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        genderInput = view.findViewById(R.id.genderInput);
        addressInput = view.findViewById(R.id.addressInput);
        emergencyContactInput = view.findViewById(R.id.emergencyContactInput);
        bloodTypeInput = view.findViewById(R.id.bloodTypeInput);
        heightInput = view.findViewById(R.id.heightInput);
        weightInput = view.findViewById(R.id.weightInput);
        allergiesInput = view.findViewById(R.id.allergiesInput);
        updateBtn = view.findViewById(R.id.udpatePatientBtn);
        tvSelectedDateP = view.findViewById(R.id.tvSelectedDateP);
        dateBtnP = view.findViewById(R.id.dateBtnP);
    }

    @Override
    public void setLayoutListeners() {
        updateBtn.setOnClickListener(this::saveUpdatedPatientDetails);
        dateBtnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    @Override
    public void initializeRequiredData() {
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        assert getArguments() != null;
        String value = getArguments().getString("id");
        Log.i("INFO", "onViewCreated: "+value);
        assert value != null;
        patient = PatientDbHelper.query(db,Integer.parseInt(value));
    }



    public void populatingUpdateFields(){
        firstNameInput.setText(patient.getFirstName());
        lastNameInput.setText(patient.getLastName());
        emailInput.setText(patient.getEmailAddress());
        phoneInput.setText(patient.getPhoneNumber());
        genderInput.setText(patient.getGender());
        addressInput.setText(patient.getAddress());
        emergencyContactInput.setText(patient.getEmergencyContact());
        bloodTypeInput.setText(patient.getBloodType());
        heightInput.setText(patient.getHeight());
        weightInput.setText(patient.getWeight());
        allergiesInput.setText(patient.getAllergies());
        tvSelectedDateP.setText(patient.getDob());
    }
}
