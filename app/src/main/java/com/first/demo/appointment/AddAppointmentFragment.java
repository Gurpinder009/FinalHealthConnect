package com.first.demo.appointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.first.demo.DbHelperClasses.AppointmentDbHelper;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.DbHelperClasses.PatientDbHelper;
import com.first.demo.R;
import com.first.demo.models.Appointment;
import com.first.demo.utilities.DataValidator;
import com.first.demo.utilities.FragmentHelper;

import java.util.Calendar;
import java.util.Locale;

public class AddAppointmentFragment extends Fragment implements FragmentHelper {


    private TextView tvSelectedDate;
    private ImageButton dateBtn;
    private TextView tvSelectedTime;
    private ImageButton timeBtn;
    private Button scheduleAppointmentBtn;

    private EditText patientNameInput;
    private EditText reasonInput;
    private int patientId;



    public AddAppointmentFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_add_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      findAndAssignLayoutElements(view);
       setLayoutListeners();
       initializeRequiredData();


    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Format the date
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);

                        // Update the TextView
                        tvSelectedDate.setText(selectedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }
    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPm = (hourOfDay < 12) ? "AM" : "PM";
                        int hour12 = (hourOfDay > 12) ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
                        String selectedTime = String.format(Locale.getDefault(),
                                "%02d:%02d %s",
                                hour12,
                                minute,
                                amPm);
                        tvSelectedTime.setText(selectedTime);
                    }
                },
                hour,
                minute,
                false // false here to use 12 hour format
        );

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @Override
    public void findAndAssignLayoutElements(View view) {
        scheduleAppointmentBtn =  view.findViewById(R.id.btnBookAppt);
        patientNameInput = view.findViewById(R.id.patientNameInput);
        reasonInput = view.findViewById(R.id.reasonInput);


        // for date picker
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        dateBtn = view.findViewById(R.id.dateBtn);

        //for timepicker
        tvSelectedTime = view.findViewById(R.id.tvSelectedTime);
        timeBtn = view.findViewById(R.id.timeBtn);
        patientNameInput.setEnabled(false);
    }

    @Override
    public void setLayoutListeners() {
    scheduleAppointmentBtn.setOnClickListener(this::addAppointment);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
    }




    public void processData(){
            reasonInput.setText(reasonInput.getText().toString().trim());
            tvSelectedTime.setText(tvSelectedTime.getText().toString().trim());
            tvSelectedDate.setText(tvSelectedDate.getText().toString().trim());
    }


    public void addAppointment(View view){
        processData();
        String reason = reasonInput.getText().toString();
        String time = tvSelectedTime.getText().toString();
        String date = tvSelectedDate.getText().toString();


        if(  DataValidator.validateFutureDate(getContext(),date) &&DataValidator.validateTime(getContext(),time) &&  DataValidator.validateText(getContext(),reason)){
            DatabaseHelper db = DatabaseHelper.getInstance(getContext());
            AppointmentDbHelper.add(db, new Appointment(reason,time,date,patientId));
            toAppointments();
        }

    }


    public void toAppointments(){
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack();
        Toast.makeText(getContext(), "Appointment is scheduled", Toast.LENGTH_SHORT).show();
    }




    @Override
    public void initializeRequiredData() {
        assert getArguments() != null;
        String idString = getArguments().getString("patient_id");
        String nameString = getArguments().getString("patient_name");
        assert idString != null;
        patientId = Integer.parseInt(idString);
        patientNameInput.setText(nameString);
    }
}