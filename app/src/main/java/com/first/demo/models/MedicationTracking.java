package com.first.demo.models;

import android.database.Cursor;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class MedicationTracking {

    private int id;
    private String medication;
    private String dosage;
    private String duration;
    private int consultationId;

    public MedicationTracking(int id, String medication, String dosage, String duration,int consultationId) {
        this.id = id;
        this.medication = medication;
        this.dosage = dosage;
        this.duration = duration;
        this.consultationId = consultationId;
    }
    public MedicationTracking(String medication, String dosage, String duration,int consultationId) {
        this.medication = medication;
        this.dosage = dosage;
        this.duration = duration;
        this.consultationId = consultationId;
    }


    public MedicationTracking(){
        this(0,"","","",0);
    }

    @NonNull
    @Override
    public String toString() {
        return "MedicationTracking{" +
                "id=" + id +
                ", medication='" + medication + '\'' +
                ", dosage='" + dosage + '\'' +
                ", number='" + duration + '\'' +
                ",consultationId"+this.consultationId +'\''+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationTracking that = (MedicationTracking) o;
        return id == that.id && Objects.equals(medication, that.medication) && Objects.equals(dosage, that.dosage) && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medication, dosage, duration);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String number) {
        this.duration = number;
    }


    public int getConsultationId(){
        return this.consultationId;
    }

    public void setConsultationId(int consultationId){
        this.consultationId = consultationId;
    }


    public static MedicationTracking fromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String medication = cursor.getString(cursor.getColumnIndexOrThrow("medication"));
        String dosage = cursor.getString(cursor.getColumnIndexOrThrow("dosage"));
        String duration = cursor.getString(cursor.getColumnIndexOrThrow("duration"));
        int consultationId = cursor.getInt(cursor.getColumnIndexOrThrow("consultation_id"));
        return new MedicationTracking(id, medication, dosage, duration,consultationId);
    }


    public static ArrayList<MedicationTracking> allFromCursor(Cursor cursor){

    ArrayList<MedicationTracking> medicationTracking=  new ArrayList<MedicationTracking>();
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String medication = cursor.getString(cursor.getColumnIndexOrThrow("medication"));
            String dosage = cursor.getString(cursor.getColumnIndexOrThrow("dosage"));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow("duration"));
            int consultationId = cursor.getInt(cursor.getColumnIndexOrThrow("consultation_id"));
            medicationTracking.add( new MedicationTracking(id, medication, dosage, duration,consultationId));
    }

        return medicationTracking;
    }
}
