package com.first.demo.DbHelperClasses;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.first.demo.models.Appointment;
import com.first.demo.models.MedicationTracking;
import com.first.demo.models.Patient;

import java.util.ArrayList;


public class MedicationTrackingDbHelper {

    private final static String TABLE_NAME = "medication_tracking";
    private final static String ID = "id";
    private final static String MEDICATION = "medication";
    private final static String DOSAGE = "dosage";
    private final static String DURATION = "duration";
    private final static String CONSULTATION_ID = "consultation_id";

    // Create table SQL statement
    public static void create(SQLiteDatabase db) {
        String sqlStatement = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MEDICATION + " TEXT NOT NULL, " +
                DOSAGE + " TEXT NOT NULL, " +
                DURATION + " TEXT NOT NULL," +
                CONSULTATION_ID + " INTEGER,"+
                "FOREIGN KEY ("+CONSULTATION_ID+") REFERENCES consultations(id)"+
                ");";
        db.execSQL(sqlStatement);
    }

    // Insert new medication tracking record
    public static long add(DatabaseHelper dbHelper, MedicationTracking medicationTracking) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEDICATION, medicationTracking.getMedication());
        values.put(DOSAGE, medicationTracking.getDosage());
        values.put(DURATION, medicationTracking.getDuration());
        values.put(CONSULTATION_ID, medicationTracking.getConsultationId());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    // Update existing medication tracking record
    public static int update(DatabaseHelper dbHelper, int id, MedicationTracking medicationTracking) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEDICATION, medicationTracking.getMedication());
        values.put(DOSAGE, medicationTracking.getDosage());
        values.put(DURATION, medicationTracking.getDuration());
        values.put(CONSULTATION_ID, medicationTracking.getConsultationId());


        String selection = ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int result = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return result;
    }

    // Delete a medication tracking record
    public static int delete(DatabaseHelper dbHelper, int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ID + " = ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NAME, selection, args);
        db.close();
        return result;
    }




    public static int deleteUsingConsultationId(DatabaseHelper dbHelper, String consultationId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = CONSULTATION_ID + " = ?";
        String[] args = {consultationId};
        int result = db.delete(TABLE_NAME, selection, args);
        db.close();
        return result;
    }

    // Query a single medication tracking record by ID
    public static MedicationTracking query(DatabaseHelper dbHelper, int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projections = {ID, MEDICATION, DOSAGE, DURATION,CONSULTATION_ID};
        String selection = ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, projections, selection, selectionArgs, null, null, null);
        MedicationTracking medicationTracking = null;

        if (cursor.moveToFirst()) {
            medicationTracking = MedicationTracking.fromCursor(cursor);
        }

        cursor.close();
        db.close();
        return medicationTracking;
    }


    public static ArrayList<MedicationTracking> queryAll(DatabaseHelper dbHelper,String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<MedicationTracking> apps;
        String[] projections = {ID,MEDICATION,DOSAGE,DURATION,CONSULTATION_ID};

        String selection = CONSULTATION_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, projections, selection,selectionArgs, null, null, null);
        apps = MedicationTracking.allFromCursor(cursor);

        cursor.close();
        db.close();
        return  apps;
    }




}