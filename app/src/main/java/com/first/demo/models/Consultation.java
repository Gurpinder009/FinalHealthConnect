package com.first.demo.models;

import android.database.Cursor;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class Consultation {
    private int id;
    private String  date;
    private String symptoms;
    private String diagnoses;
    private String treatments;
    private int patientId;
    private String patientName;



    public Consultation(int id, String date, String symptoms, String diagnoses, String treatments,int patientId) {
        this.id = id;
        this.date = date;
        this.symptoms = symptoms;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
        this.patientId = patientId;
    }
    public Consultation(String date, String symptoms, String diagnoses, String treatments,int patientId) {
        this.date = date;
        this.symptoms = symptoms;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
        this.patientId = patientId;
    }

    public Consultation() {
        this(0,null,"","","",0);
    }

    @NonNull
    @Override
    public String toString() {
        return "Consultations{" +
                "id=" + id +
                ", date=" + date +
                ", symptoms='" + symptoms + '\'' +
                ", diagnoses='" + diagnoses + '\'' +
                ", treatments='" + treatments + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consultation that = (Consultation) o;
        return id == that.id && Objects.equals(date, that.date)  && Objects.equals(symptoms, that.symptoms) && Objects.equals(diagnoses, that.diagnoses) && Objects.equals(treatments, that.treatments) && Objects.equals(patientId, that.patientId) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, symptoms, diagnoses, treatments,patientId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }


    public int getPatientId() {
        return patientId;
    }
    public String  getPatientName(){return patientName;}

    public void setPatientName(String patientName){
        this.patientName = patientName;
    }
    public void setDate(String date) {
        this.date = date;
    }

public void setPatientId(int patientId){
        this.patientId = patientId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(String diagnoses) {
        this.diagnoses = diagnoses;
    }

    public String getTreatments() {
        return treatments;
    }

    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }


    public static Consultation fromCursor(Cursor cursor) {
        Consultation consultation = new Consultation();
        consultation.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        consultation.setSymptoms(cursor.getString(cursor.getColumnIndexOrThrow("symptoms")));
        consultation.setDiagnoses(cursor.getString(cursor.getColumnIndexOrThrow("diagnoses")));
        consultation.setTreatments(cursor.getString(cursor.getColumnIndexOrThrow("treatments")));
        consultation.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
        consultation.setPatientId(cursor.getInt(cursor.getColumnIndexOrThrow("patient_id")));
        return consultation;
    }



    public static ArrayList<Consultation> allFromCursor(Cursor cursor){
        ArrayList<Consultation> consultations=  new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Consultation consultation = new Consultation();
                consultation.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                consultation.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                consultation.setPatientId(cursor.getInt(cursor.getColumnIndexOrThrow("patient_id")));

                consultations.add(consultation);
            }while(cursor.moveToNext());
        }
        return consultations;
    }
}
