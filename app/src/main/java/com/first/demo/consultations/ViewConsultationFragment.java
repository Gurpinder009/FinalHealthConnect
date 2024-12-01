package com.first.demo.consultations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.first.demo.DbHelperClasses.AppointmentDbHelper;
import com.first.demo.DbHelperClasses.ConsultationDbHelper;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.DbHelperClasses.MedicationTrackingDbHelper;
import com.first.demo.R;
import com.first.demo.adaptors.AppointmentRecycleViewAdaptor;
import com.first.demo.adaptors.MedicationTrackingRecycleViewAdaptor;
import com.first.demo.appointment.ViewAppointmentFragment;
import com.first.demo.models.Appointment;
import com.first.demo.models.Consultation;
import com.first.demo.models.MedicationTracking;
import com.first.demo.utilities.FragmentHelper;

import java.util.ArrayList;

public class ViewConsultationFragment extends Fragment implements FragmentHelper {

    RecyclerView medicationTrackingRecycleView;
    ArrayList<MedicationTracking> medications;
    Consultation consultation;




    TextView consultationDate;
    TextView consultationDiagnoses;
    TextView consultationTreatment;
    TextView consultationSymptoms;



    public ViewConsultationFragment() {
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_consultation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);
        initializeRequiredData();
        setLayoutListeners();
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);
        initializeRequiredData();
        medicationTrackingRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        MedicationTrackingRecycleViewAdaptor adaptor = new MedicationTrackingRecycleViewAdaptor(getContext(), medications);
        medicationTrackingRecycleView.setAdapter(adaptor);

    }

    @Override
    public void findAndAssignLayoutElements(View view) {
        medicationTrackingRecycleView = view.findViewById(R.id.medicationTrackingRecycleView);
        consultationDate = view.findViewById(R.id.consultationDate);
        consultationDiagnoses = view.findViewById(R.id.consultationDiagnoses);
        consultationTreatment = view.findViewById(R.id.consultationTreatment);
        consultationSymptoms = view.findViewById(R.id.consultationsSymptoms);

    }

    @Override
    public void setLayoutListeners() {

    }

    @Override
    public void initializeRequiredData() {

        assert getArguments() != null;
        String value = getArguments().getString("id");
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        assert value != null;
        consultation = ConsultationDbHelper.query(db,Integer.parseInt(value));
//        consultation = new Consultation();
        medications = MedicationTrackingDbHelper.queryAll(db, value);

        String consultationDateString = "Date: "+consultation.getDate();
        String consultationDiagnosesString = "Diagnoses: "+consultation.getDiagnoses();
        String consultationTreatmentString  = "Treatment: "+consultation.getTreatments();
        String consultationSymptomsString = "Symptoms: "+ consultation.getSymptoms();

        consultationDate.setText(consultationDateString);
        consultationTreatment.setText(consultationTreatmentString);
        consultationDiagnoses.setText(consultationDiagnosesString);
        consultationSymptoms.setText(consultationSymptomsString);





    }
}