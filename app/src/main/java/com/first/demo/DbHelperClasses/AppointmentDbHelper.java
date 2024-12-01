package com.first.demo.DbHelperClasses;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.first.demo.models.Appointment;
import com.first.demo.models.Patient;

import java.sql.Time;
import java.util.ArrayList;

public class AppointmentDbHelper {


     final static String TABLE_NAME = "appointments";
     final static String ID = "id";
     final static String REASON = "reason";
     final static String DATE = "date";
     final static String IS_CANCELLED = "is_cancelled";
     final static String PATIENT_ID ="patient_id";
     final static String TIME = "appointment_time";

    private PatientDbHelper patientDbHelper;
    // Create table SQL statement
    public static void create(SQLiteDatabase db) {
        String sqlStatement = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REASON + " TEXT NOT NULL, " +
                DATE + " TEXT NOT NULL," +
                TIME + " TIME NOT NULL," +
                IS_CANCELLED + " INT NOT NULL DEFAULT 1,"+
                PATIENT_ID + " INTEGER,"+
                "FOREIGN KEY ("+PATIENT_ID+") REFERENCES patients(id)"+
                ");";
        db.execSQL(sqlStatement);
    }

    // Insert new appointment
    public static long add(DatabaseHelper dbHelper, Appointment appointment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(REASON, appointment.getReason());
        values.put(DATE, appointment.getDate());
        values.put(TIME,appointment.getTime());
        values.put(PATIENT_ID,appointment.getPatientId());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    // Update existing appointment
    public static int update(DatabaseHelper dbHelper, int id, Appointment appointment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REASON, appointment.getReason());
        values.put(DATE, appointment.getDate());
        values.put(TIME,appointment.getTime());
        values.put(PATIENT_ID,appointment.getPatientId());


        String selection = ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int result = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return result;
    }

    // Delete an appointment
    public static int delete(DatabaseHelper dbHelper, int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ID + " = ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NAME, selection, args);
        db.close();
        return result;
    }



    public static int deleteAppointmentUsingPatientId(DatabaseHelper dbHelper,String patient_id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = PATIENT_ID + " = ?";
        String[] args = {patient_id};
        int result = db.delete(TABLE_NAME, selection, args);
        db.close();
        return result;
    }


    public static int cancelAppointment(DatabaseHelper dbHelper, int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IS_CANCELLED, 0);
        String selection = ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        int result = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return result;
    }



    public static ArrayList<Appointment> queryAll(DatabaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Appointment> apps;
        String[] projections = {DATE,ID,TIME,PATIENT_ID};

        String selection = IS_CANCELLED + " = ?";
        String[] selectionArgs = {String.valueOf(1)};

        Cursor cursor = db.query(TABLE_NAME, projections, selection,selectionArgs, null, null, null);
        apps = Appointment.allFromCursor(cursor);

        for (Appointment a: apps) {
            Patient p = PatientDbHelper.query(dbHelper,a.getPatientId());
            a.setPatientName(p.getFirstName()+" "+p.getLastName());
        }
        cursor.close();
        db.close();
        return  apps;
    }
    //filter appoint with name
//    public static ArrayList<Appointment> queryAllWithLike(DatabaseHelper dbHelper, String searchQuery) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        ArrayList<Appointment> apps = new ArrayList<>();
//
//        String[] projections = {DATE, ID, PATIENT_ID};
//
//
//        Cursor cursor = db.query(TABLE_NAME, projections, null, null, null, null, null);
//
//        if (cursor != null) {
//            apps = Appointment.allFromCursor(cursor);
//
//            for (Appointment a : apps) {
//                // Fetch patient details and set the name for each appointment
//                Patient p = PatientDbHelper.query(dbHelper, a.getPatientId());
//                if (p != null) {
//                    a.setPatientName(p.getFirstName()+" "+p.getLastName());
//                }
//            }
//
//            cursor.close();
//        }
//
//        db.close();
//
//        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
//            String lowerCaseQuery = searchQuery.toLowerCase();
//            ArrayList<Appointment> filteredApps = new ArrayList<>();
//            for (Appointment a : apps) {
//                if (a.getPatientName() != null && a.getPatientName().toLowerCase().contains(lowerCaseQuery)) {
//                    filteredApps.add(a);
//                }
//            }
//            return filteredApps;
//        }
//
//        return apps; // Return all appointments if no search query is provided
//    }

    // Query a single appointment by ID
    public static Appointment query(DatabaseHelper dbHelper, int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projections = {ID, REASON, TIME, DATE,PATIENT_ID};
        String selection = ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, projections, selection, selectionArgs, null, null, null);
        Appointment appointment = Appointment.fromCursor(cursor);
        Patient patient = PatientDbHelper.query(dbHelper,appointment.getPatientId());

        appointment.setPatientName(patient.getFirstName()+" "+patient.getLastName());

        cursor.close();
        db.close();
        return appointment;
    }


    public static void seedAppointments(DatabaseHelper dbHelper){

        AppointmentDbHelper.add(dbHelper,new Appointment(2,"fever","23:59:59.999999","2000-11-10",2));
        AppointmentDbHelper.add(dbHelper,new Appointment(3,"fever","23:59:59.999999","2000-11-10",1));
        AppointmentDbHelper.add(dbHelper,new Appointment(4,"fever","23:59:59.999999","2000-11-10",4));
        AppointmentDbHelper.add(dbHelper,new Appointment(5,"fever","23:59:59.999999","2000-11-10",3));
        AppointmentDbHelper.add(dbHelper,new Appointment(6,"fever","23:59:59.999999","2000-11-10",2));

    }

}
