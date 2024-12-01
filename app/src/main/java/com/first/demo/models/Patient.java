package com.first.demo.models;

import android.database.Cursor;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String dob;
    private String address;
    private String gender;
    private String emergencyContact;
    private String bloodType;
    private String height;
    private String weight;
    private String allergies;
    private int isActive;



    public Patient(){
        this(0,"","","","",null,"","","","","","","",0);

    }
    public Patient( String firstName,String lastName, String emailAddress, String phoneNumber, String dob, String address, String gender, String emergencyContact, String bloodType, String height, String weight, String allergies, int isActive){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.dob    = dob;
        this.address = address;
        this.gender = gender;
        this.emergencyContact = emergencyContact;
        this.bloodType = bloodType;
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
        this.isActive = isActive;
    }



    public Patient(int id, String firstName,String lastName, String emailAddress, String phoneNumber, String dob, String address, String gender, String emergencyContact, String bloodType, String height, String weight, String allergies, int isActive){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.dob    = dob;
        this.address = address;
        this.gender = gender;
        this.emergencyContact = emergencyContact;
        this.bloodType = bloodType;
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
        this.isActive = isActive;
    }


    @Override
    public String toString() {
        return getFirstName();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patients = (Patient) o;
        return id == patients.id && Objects.equals(firstName, patients.firstName) && Objects.equals(emailAddress, patients.emailAddress) && Objects.equals(phoneNumber, patients.phoneNumber) && Objects.equals(dob, patients.dob) && Objects.equals(address, patients.address) && Objects.equals(gender, patients.gender) && Objects.equals(emergencyContact, patients.emergencyContact) && Objects.equals(bloodType, patients.bloodType) && Objects.equals(height, patients.height) && Objects.equals(weight, patients.weight) && Objects.equals(allergies, patients.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, emailAddress, phoneNumber, dob, address, gender, emergencyContact, bloodType, height, weight, allergies);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){return lastName;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName){this.lastName = lastName;}

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public int getIsActive(){
       return this.isActive;
    }
    public void setIsActive(int isActive){
        this.isActive = isActive;
    }


    public static  Patient fromCursor(Cursor cursor){
        Patient patient = new Patient();


        patient.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        patient.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow("first_name")));
        patient.setLastName(cursor.getString(cursor.getColumnIndexOrThrow("last_name")));

        patient.setEmailAddress(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        patient.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow("phone_number")));
        patient.setDob(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
        patient.setGender(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
        patient.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
        patient.setEmergencyContact(cursor.getString(cursor.getColumnIndexOrThrow("emergency_contact")));
        patient.setBloodType(cursor.getString(cursor.getColumnIndexOrThrow("blood_type")));
        patient.setHeight(cursor.getString(cursor.getColumnIndexOrThrow("height")));
        patient.setWeight(cursor.getString(cursor.getColumnIndexOrThrow("weight")));
        patient.setAllergies(cursor.getString(cursor.getColumnIndexOrThrow("allergies")));
        patient.setIsActive(cursor.getInt(cursor.getColumnIndexOrThrow("is_active")));


        return patient;
    }


    public static String[] allFromCursorStrings(Cursor cursor){
        String[] patients =  new String[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext()){
            patients[i] =cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
            i++;

        }

return patients;


    }



    public static  ArrayList<Patient> allFromCursorSmall(Cursor cursor){
        ArrayList<Patient> patients =  new ArrayList<>();
        if(cursor.moveToFirst()){

            do{
                Patient patient = new Patient();
                patient.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                patient.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow("first_name")));
                patient.setLastName(cursor.getString(cursor.getColumnIndexOrThrow("last_name")));
                patients.add(patient);

            }while(cursor.moveToNext());
        }
        return patients;
    }



    public static  Patient[] allFromCursor(Cursor cursor){
        Patient[] patients =  new Patient[cursor.getCount()];
        Log.i("patient", "allFromCursor: "+cursor.getCount());
        int i=0;
        while(cursor.moveToNext()){

            Patient patient = new Patient();

            patient.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            patient.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow("first_name")));
            patient.setLastName(cursor.getString(cursor.getColumnIndexOrThrow("last_name")));

            patient.setEmailAddress(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            patient.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow("phone_number")));
            patient.setDob(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
            patient.setGender(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
            patient.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            patient.setEmergencyContact(cursor.getString(cursor.getColumnIndexOrThrow("emergency_contact")));
            patient.setBloodType(cursor.getString(cursor.getColumnIndexOrThrow("blood_type")));
            patient.setHeight(cursor.getString(cursor.getColumnIndexOrThrow("height")));
            patient.setWeight(cursor.getString(cursor.getColumnIndexOrThrow("weight")));
            patient.setAllergies(cursor.getString(cursor.getColumnIndexOrThrow("allergies")));
            patient.setIsActive(cursor.getInt(cursor.getColumnIndexOrThrow("is_active")));

            patients[i] =patient;

        }

        return patients;


    }












}
