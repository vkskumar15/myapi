package com.codesolution.mynewapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codesolution.mynewapi.Models.UniqueUserNameModel;
import com.codesolution.mynewapi.databinding.ActivityRegisterBinding;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private ViewModelClass modelClass;
    private String userName, password, strDate, time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });

        binding.datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date= year+"/"+(month+1)+"/"+dayOfMonth;
                        binding.tvDate.setText(date);
                    }
                },year,month,day);
                dialog.show();
            }
        });

        binding.timePick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RegisterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        binding.tvTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


    }
    private Boolean validateUserName() {
        userName = binding.user.getText().toString();
        String user = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
        if (userName.isEmpty()) {
            binding.user.setError("Field cannot be empty");
            return false;
        } else if (!userName.matches(user)) {
            binding.user.setError("Min 5 character Required and Space not Allowed");
            return false;

        } else {
            binding.user.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        password = binding.password.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (password.isEmpty()) {
            binding.password.setError("Field cannot be empty");
            return false;
        } else if (!password.matches(passwordVal)) {
            binding.password.setError("Password is too weak");
            return false;
        } else {
            binding.password.setError(null);
            return true;
        }
    }

    private void registerUser(){

        strDate = binding.tvDate.getText().toString().trim();
        time= binding.tvTime.getText().toString().trim();


        if (!validatePassword() | !validateUserName())
        {
            return;
        }

        if (strDate.isEmpty())
        {
            binding.tvDate.setError("Date is Required");
            binding.tvDate.setFocusable(true);
            return;
        }
        if (time.isEmpty())
        {
            binding.tvTime.setError("Time is Required");
            binding.tvTime.setFocusable(true);
            return;
        }

        else {

            App.getSingleton().setUserName(userName);
            App.getSingleton().setPassword(password);
            App.getSingleton().setDate(strDate);
            Toast.makeText(RegisterActivity.this, App.getSingleton().getDate(), Toast.LENGTH_SHORT).show();
            App.getSingleton().setTime(time);


            modelClass = ViewModelProviders.of(this).get(ViewModelClass.class);

            modelClass.uniqueDataMethod(RegisterActivity.this, userName).observe(this, new Observer<UniqueUserNameModel>() {
                @Override
                public void onChanged(UniqueUserNameModel uniqueUserNameModel) {

                    if (uniqueUserNameModel.getSuccess().equalsIgnoreCase("1"))
                    {
                        startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));

                        Toast.makeText(RegisterActivity.this, uniqueUserNameModel.getMessage(), Toast.LENGTH_LONG).show();

                    }else if (uniqueUserNameModel.getSuccess().equalsIgnoreCase("0")) {

                        Toast.makeText(RegisterActivity.this, uniqueUserNameModel.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }
            });


        }

    }

}