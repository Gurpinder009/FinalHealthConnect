package com.first.demo.models;

import android.database.Cursor;

import androidx.annotation.NonNull;

import com.first.demo.DbHelperClasses.AppointmentDbHelper;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.DbHelperClasses.PatientDbHelper;

import java.util.ArrayList;
import java.util.Objects;

public class Appointment {
    private int id;
    private String reason;
    private String date;
    private String time;
    private int patientId;
    private String patientName;

    public Appointment(int id, String reason,String time,String date,int patientId) {
        this.id = id;
        this.reason = reason;
        this.time = time;
        this.patientId = patientId;
        this.date = date;

    }


    public Appointment( String reason,String time,String date,int patientId) {

        this.reason = reason;
        this.time = time;
        this.patientId = patientId;
        this.date = date;

    }



    public Appointment(){
        this(0,""," ",null,0);
    }



    @NonNull
    @Override
    public String toString() {
        return "Appointments{" +
                "id=" + id +
                ", reason='" + reason + '\'' +

                ", date='" + date + '\'' +
                ", patient Id='" + patientId + '\'' +


                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id && Objects.equals(reason, that.reason)  && Objects.equals(date, that.date) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reason, date);
    }


    public int getId() {
        return id;
    }

    public String getPatientName(){
        return patientName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatientName(String patientName){
        this.patientName = patientName;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }



    public String getTime(){return time;}

    public String getDate() {
        return date;
    }
    public int getPatientId(){return patientId;}
    public void setPatientId(int patientId ){this.patientId= patientId;}
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time){this.time = time;}


    public static Appointment fromCursor(Cursor cursor) {
        Appointment appointment = new Appointment();
        if(cursor.moveToFirst()){

            appointment.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            appointment.setReason(cursor.getString(cursor.getColumnIndexOrThrow("reason")));
            appointment.setTime(cursor.getString(cursor.getColumnIndexOrThrow("appointment_time")));

            appointment.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
            appointment.setPatientId(cursor.getInt(cursor.getColumnIndexOrThrow("patient_id")));
        }

        return appointment;

    }

    public static ArrayList<Appointment> allFromCursor(Cursor cursor){
        ArrayList<Appointment> appointments=  new ArrayList<Appointment>();
        while(cursor.moveToNext()){
            Appointment appointment = new Appointment();
            appointment.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
            appointment.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            appointment.setTime(cursor.getString(cursor.getColumnIndexOrThrow("appointment_time")));
            appointment.setPatientId(cursor.getInt(cursor.getColumnIndexOrThrow("patient_id")));
            appointments.add(appointment);
        }

        return appointments;

    }




    public static String[] allFromCursorStrings(Cursor cursor){
        String[] appointments=  new String[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext()){
            appointments[i] =cursor.getString(cursor.getColumnIndexOrThrow("date"));

            i++;

        }

        return appointments;

    }
}
