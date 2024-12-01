package com.first.demo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.appointment.ViewAppointmentsFragment;
import com.first.demo.consultations.ConsultationsFragment;
import com.first.demo.databinding.ActivityMainBinding;
import com.first.demo.patient.ViewPatientsFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ViewPatientsFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.patients) {
                replaceFragment(new ViewPatientsFragment());
                return true;
            } else if (item.getItemId() == R.id.appointments) {
                replaceFragment(new ViewAppointmentsFragment());
                return true;
            } else if (item.getItemId() == R.id.consultations) {
                replaceFragment(new ConsultationsFragment());
                return true;
            }
                    return false;
        });




        db = DatabaseHelper.getInstance(getApplicationContext());
//        PatientDbHelper.seedPatients(db);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }





    public  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.addToBackStack("name");
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();
    }


    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}



