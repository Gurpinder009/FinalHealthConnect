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

import com.first.demo.DbHelperClasses.ConsultationDbHelper;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.R;
import com.first.demo.adaptors.ConsultationRecycleViewAdaptor;
import com.first.demo.appointment.ViewAppointmentFragment;
import com.first.demo.models.Appointment;
import com.first.demo.models.Consultation;
import com.first.demo.utilities.FragmentHelper;

import java.util.ArrayList;

public class ConsultationsFragment extends Fragment implements FragmentHelper {

    ArrayList<Consultation> consultations;
    ConsultationRecycleViewAdaptor adaptor;
    RecyclerView consultationRecycleView;



    public ConsultationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consultations, container, false);
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
        consultationRecycleView = view.findViewById(R.id.consultationsRecyclerView);

    }

    @Override
    public void setLayoutListeners() {


    }


    //moving to updatePatient Fragment
    public void toViewConsultation(int position){

    }

    @Override
    public void initializeRequiredData() {
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());

        consultations = ConsultationDbHelper.queryAll(db);
        consultationRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptor = new ConsultationRecycleViewAdaptor(getContext(), consultations,  position -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment newFrag = new ViewConsultationFragment();
            Consultation c = consultations.get(position);
            Bundle args = new Bundle();

            args.putString("id",String.valueOf(c.getId()));
            newFrag.setArguments(args);
            ft.replace(R.id.frameLayout,newFrag);
            ft.setReorderingAllowed(true);
            ft.addToBackStack("name");
            ft.commit();
        });

        consultationRecycleView.setAdapter(adaptor);

    }
}