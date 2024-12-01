package com.first.demo.utilities;

import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataValidator {
    
    
    
    public static boolean validateFirstName(Context context, String firstName){
        if (firstName == null || firstName.isEmpty()) {
            Toast.makeText(context,"first name can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if (!firstName.matches("[a-zA-Z\\s]+")) {
                Toast.makeText(context,"First name is invalid", Toast.LENGTH_LONG).show();

                return false;
            }else{
                if (firstName.length() < 2 || firstName.length() > 50) {
                    Toast.makeText(context,"First name is too short or long", Toast.LENGTH_LONG).show();

                    return false;
                }else{
                    return true;

                }
            }
        }
    }



    public static boolean validateLastName(Context context,String lastName){
        if (lastName == null || lastName.isEmpty()) {
            Toast.makeText(context,"Last name can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if (!lastName.matches("[a-zA-Z\\s]+")) {
                Toast.makeText(context,"invalid last name", Toast.LENGTH_LONG).show();

                return false;
            }else{
                if (lastName.length() < 2 || lastName.length() > 50) {
                    Toast.makeText(context,"Last Name is too short or long", Toast.LENGTH_LONG).show();

                    return false;
                }else{
                    return true;

                }
            }
        }
    }


    public static boolean validateEmail(Context context,String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";


        if (email == null || email.trim().isEmpty()) {
            Toast.makeText(context,"Email address can't be empty", Toast.LENGTH_LONG).show();

            return false;
        }else{

            if (!email.matches(emailPattern)) {
                Toast.makeText(context,"invalid email address", Toast.LENGTH_LONG).show();

                return false;
            }else{
                if (email.length() < 5 || email.length() > 255) {
                    Toast.makeText(context,"Email address is too short or long", Toast.LENGTH_LONG).show();

                    return false;
                }else{
                    return true;
                }
            }
        }
    }




    public static  boolean validatePhoneNumber(Context context,String phoneNumber) {
        String phonePattern = "^[+]?[0-9]{10,15}$";

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {

            Toast.makeText(context,"Phone Number can not be empty", Toast.LENGTH_LONG).show();

            return false;
        }else{
            if (!phoneNumber.matches(phonePattern)) {
                Toast.makeText(context,"Invalid phone number", Toast.LENGTH_LONG).show();

                return false;
            }else{
                if (phoneNumber.length() < 10 || phoneNumber.length() > 15) {
                    Toast.makeText(context,"Phone number is too short or long", Toast.LENGTH_LONG).show();

                    return false;
                }else{
                    return true;

                }
            }
        }
    }



    public static boolean validateGender(Context context, String gender){
        if (gender == null || gender.trim().isEmpty()) {

            Toast.makeText(context,"gender can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }



    public static boolean validateAddress(Context context,String address){
        if (address == null || address.isEmpty()) {
            Toast.makeText(context,"Address can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }else{

            if (address.length() < 3 ) {
                    Toast.makeText(context,"Address is too short or long", Toast.LENGTH_LONG).show();

                    return false;
            }else{
                    return true;

            }
        }
    }



    public static boolean validateBloodType(Context context,String bloodType) {
        String normalizedBloodType = bloodType.toUpperCase();
        String[] validBloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

        if (normalizedBloodType.isEmpty()) {
            Toast.makeText(context, "Blood type can't be empty", Toast.LENGTH_LONG).show();
            return false; // Invalid: Blood type is empty
        }

        for (String validType : validBloodTypes) {
            if (normalizedBloodType.equals(validType)) {
                return true;
            }
        }
        Toast.makeText(context, "Invalid Blood Group", Toast.LENGTH_LONG).show();


        return false;
    }



    public static boolean validateHeight(Context context, String height) {
        if (height == null || height.isEmpty()) {
            Toast.makeText(context, "Height can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {
            try {
                int heightValue = Integer.parseInt(height);

                if (heightValue < 30 || heightValue > 300) {
                    Toast.makeText(context, "Height should be between 30 cm and 300 cm", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    return true;
                }

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Invalid height format", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }



    public static boolean validateWeight(Context context, String weight) {
        if (weight == null || weight.isEmpty()) {
            Toast.makeText(context, "Weight can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {
            try {
                int weightValue = Integer.parseInt(weight);

                if (weightValue < 2 || weightValue > 500) {
                    Toast.makeText(context, "Weight should be between 2 kg and 500 kg", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    return true;
                }

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Invalid weight format", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    public static boolean validateDOB(Context context, String dob) {
        if (dob == null || dob.isEmpty()) {
            Toast.makeText(context, "Date of birth can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            try {
                Date dobDate = sdf.parse(dob);

                assert dobDate != null;
                if (dobDate.after(new Date())) {
                    Toast.makeText(context, "Date of birth cannot be in the future", Toast.LENGTH_LONG).show();
                    return false;
                }



                return true;

            } catch (ParseException e ) {
                Toast.makeText(context, "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }



    public static boolean validateTime(Context context, String time) {
        if (time == null || time.isEmpty()) {
            Toast.makeText(context, "Time can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public static boolean validateFutureDate(Context context, String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            Toast.makeText(context, "Date can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            try {
                Date inputDate = sdf.parse(dateStr);

                if (inputDate == null) {
                    Toast.makeText(context, "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (inputDate.before(new Date())) {
                    Toast.makeText(context, "Appointment can't be scheduled for past", Toast.LENGTH_LONG).show();
                    return false;
                }

                return true;

            } catch (ParseException e) {
                Toast.makeText(context, "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }




    public static boolean validateText(Context context, String text){
        if (text == null || text.isEmpty()) {
            Toast.makeText(context,"column can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public static boolean validateDate(Context context, String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            Toast.makeText(context, "Date can't be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Disable lenient parsing (no forgiving of invalid dates)

            try {
                Date inputDate = sdf.parse(dateStr);

                assert inputDate != null;
                if (inputDate.after(new Date())) { // Check if the date is in the future
                    Toast.makeText(context, "Date cannot be in the future", Toast.LENGTH_LONG).show();
                    return false;
                }

                return true;

            } catch (ParseException e) {
                // If parsing fails, show an error message
                Toast.makeText(context, "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

}

