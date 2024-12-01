package com.first.demo.DbHelperClasses;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.first.demo.models.Patient;

import java.sql.Date;
import java.util.ArrayList;


public class PatientDbHelper{
    private final static String TABLE_NAME="patients";
    private final static String ID = "id";
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String EMAIL = "email";
    private final static String PHONE_NUMBER = "phone_number";
    private final static String DOB = "dob";
    private final static String GENDER = "gender";
    private final static String ADDRESS = "address";
    private final static String EMERGENCY_CONTACT = "emergency_contact";
    private final static String BLOOD_TYPE = "blood_type";
    private final static String HEIGHT = "height ";
    private final static String WEIGHT = "weight";
    private final static String ALLERGIES = "allergies";
    private final static String  CREATED_AT= "created_at";
    private final static String UPDATED_AT ="updated_at";
    private final static String IS_ACTIVE="is_active";



    public static void create(SQLiteDatabase db){
        String sqlStatement ="CREATE TABLE  patients(" +
                ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                FIRST_NAME+" TEXT NOT NULL,"+
                LAST_NAME+" TEXT NOT NULL,"+
                EMAIL+" TEXT,"+
                PHONE_NUMBER + " TEXT NOT NULL,"+
                DOB+ " DATETIME,"+
                GENDER+ " TEXT,"+
                ADDRESS+ " TEXT NOT NULL,"+
                EMERGENCY_CONTACT+ " TEXT,"+
                BLOOD_TYPE+" TEXT,"+
                HEIGHT+" TEXT,"+
                WEIGHT+" TEXT,"+
                IS_ACTIVE+" INTEGER DEFAULT 1,"+
                ALLERGIES+" TEXT,"+
                CREATED_AT+" DATETIME,"+
                UPDATED_AT+" DATETIME"+
                ");";

       db.execSQL(sqlStatement);
    }

    public static long add(DatabaseHelper dbHelper, Patient patient) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, patient.getFirstName());
        values.put(LAST_NAME, patient.getLastName());
        values.put(EMAIL, patient.getEmailAddress());
        values.put(PHONE_NUMBER, patient.getPhoneNumber());
        values.put(DOB, String.valueOf(patient.getDob()));
        values.put(GENDER, patient.getGender());
        values.put(ADDRESS, patient.getAddress());
        values.put(EMERGENCY_CONTACT, patient.getEmergencyContact());
        values.put(BLOOD_TYPE, patient.getBloodType());
        values.put(HEIGHT, patient.getHeight());
        values.put(WEIGHT, patient.getWeight());
        values.put(ALLERGIES, patient.getAllergies());
        values.put(CREATED_AT, System.currentTimeMillis());

        values.put(IS_ACTIVE, patient.getIsActive());
        long result = db.insert(TABLE_NAME,null,values);
        db.close();
        return  result;
    }


    public static int update(DatabaseHelper dbHelper,Patient patient) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, patient.getFirstName());
        values.put(LAST_NAME, patient.getLastName());
        values.put(EMAIL, patient.getEmailAddress());
        values.put(PHONE_NUMBER, patient.getPhoneNumber());
        values.put(DOB, String.valueOf(patient.getDob()));
        values.put(GENDER, patient.getGender());
        values.put(ADDRESS, patient.getAddress());
        values.put(EMERGENCY_CONTACT, patient.getEmergencyContact());
        values.put(BLOOD_TYPE, patient.getBloodType());
        values.put(HEIGHT, patient.getHeight());
        values.put(WEIGHT, patient.getWeight());
        values.put(ALLERGIES, patient.getAllergies());
        values.put(UPDATED_AT, String.valueOf(System.currentTimeMillis()));
        values.put(IS_ACTIVE, 1);

        String selection = ID + " = ?";
        String[] selectionArgs = {String.valueOf(patient.getId())};

        int result = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return result;
    }

    public  static void delete(DatabaseHelper dbHelper, int id) {
        ConsultationDbHelper.deleteUsingPatientId(dbHelper, String.valueOf(id));
        AppointmentDbHelper.deleteAppointmentUsingPatientId(dbHelper,String.valueOf(id));
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IS_ACTIVE, 0);
        String selection = ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        int result = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }



    public static Patient query(DatabaseHelper dbHelper,int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[]  projections = {
                ID,
                FIRST_NAME,
                LAST_NAME,
                EMAIL,
                PHONE_NUMBER,
                DOB,
                GENDER,
                ADDRESS,
                EMERGENCY_CONTACT,
                BLOOD_TYPE,
                HEIGHT,
                WEIGHT,
                ALLERGIES,
                CREATED_AT,
                UPDATED_AT,
                IS_ACTIVE

        };
        String  selection = ID +" = ? AND " + IS_ACTIVE +" = ? ";
        String[] args = {String.valueOf(id),String.valueOf(1)};

        Cursor cursor = db.query(TABLE_NAME,projections,selection,args,null,null, null);
        Patient patient = new Patient();
        if(cursor.moveToFirst()){
            patient = Patient.fromCursor(cursor);

        }
        cursor.close();
        db.close();
        return patient;
    }


    public static ArrayList<Patient> queryAll(DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[]  projections = {
                ID,
                FIRST_NAME,
                LAST_NAME,



        };
        String selection = IS_ACTIVE + " = ?";
        String[] selectionArgs = {String.valueOf(1)};
        Cursor cursor = db.query(TABLE_NAME,projections,selection,selectionArgs,null,null, null);
        ArrayList<Patient> patients = Patient.allFromCursorSmall(cursor);
        cursor.close();
        db.close();
        return patients;
    }


    public static ArrayList<Patient> queryAllWithLike(DatabaseHelper dbHelper,String likeString ) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[]  projections = {
                ID,
                FIRST_NAME,
                LAST_NAME,



        };
        String selection = IS_ACTIVE + " = ? AND first_name like '%"+ likeString +"%'";
        String[] selectionArgs = {String.valueOf(1)};
        Cursor cursor = db.query(TABLE_NAME,projections,selection,selectionArgs,null,null, null);
        ArrayList<Patient> patients = Patient.allFromCursorSmall(cursor);
        cursor.close();

        db.close();
        return patients;
    }




//    public static String[] queryAllStrings(DatabaseHelper dbHelper) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String[]  projections = {
//                FIRST_NAME
//        };
//
//
//        String selection = IS_ACTIVE + " = ?";
//        String[] selectionArgs = {String.valueOf(1)};
//
//
//        Cursor cursor = db.query(TABLE_NAME,projections,selection,selectionArgs,null,null, null);
//
//        String[] patients = Patient.allFromCursorStrings(cursor);
//        cursor.close();
//        db.close();
//        return patients;
//    }



    public static void seedPatients(DatabaseHelper dbHelper){

        PatientDbHelper.add(dbHelper,new Patient(1,"Morgan","smith","email@gmail.com","8999434943","2024/10/3","address s","3243423232","A+","232g","80kg","asdfasd asdfas","",1));
        PatientDbHelper.add(dbHelper,new Patient(2,"andy", "willam","email@gmail.com","8999434943","2024/11/9","address s","3243423232","A+","232g","80kg","asdfasd asdfas","",1));
        PatientDbHelper.add(dbHelper,new Patient(3,"sauarv","virdi","email@gmail.com","8999434943","2024/11/10","address s","3243423232","A+","232g","80kg","asdfasd asdfas","",1));

    }




}
