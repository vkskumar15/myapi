package com.codesolution.mynewapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codesolution.mynewapi.Models.LoginModel;
import com.codesolution.mynewapi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private String str_name, str_password;
    private ViewModelClass viewModelClass;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModelClass = ViewModelProviders.of(this).get(ViewModelClass.class);


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

    }

    private Boolean validateUserName() {
       str_name = binding.user.getText().toString();
        String user = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
        if (str_name.isEmpty()) {
            binding.user.setError("Field cannot be empty");
            return false;
        } else if (!str_name.matches(user)) {
            binding.user.setError("Min 5 character Required and Space not Allowed");
            return false;

        } else {
            binding.user.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        str_password = binding.password.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (str_password.isEmpty()) {
            binding.password.setError("Field cannot be empty");
            return false;
        } else if (!str_password.matches(passwordVal)) {
            binding.password.setError("Password is too weak");
            return false;
        } else {
            binding.password.setError(null);
            return true;
        }
    }

    private void login() {
        if (!validatePassword() | !validateUserName())
        {
            return;
        }
        else {

            viewModelClass.matchDataMethod(this, str_name, str_password).observe(this, new Observer<LoginModel>() {
                @Override
                public void onChanged(LoginModel loginModel) {
                    if (loginModel.getSuccess().equalsIgnoreCase("1")) {

                        Toast.makeText(MainActivity.this, loginModel.getMessage(), Toast.LENGTH_LONG).show();

                        App.getSingleton().setUserName(loginModel.getDetails().getUsername());

                        App.getSingleton().setPassword(loginModel.getDetails().getPassword());

                        App.getSingleton().setDateLogin(loginModel.getDetails().getJoiningDate());

                        App.getSingleton().setTime(loginModel.getDetails().getJoiningTime());

                        App.getSingleton().setImage(loginModel.getDetails().getImage());

                        App.getSingleton().setSpinner(loginModel.getDetails().getProfession());

                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(MainActivity.this, DetailsActivity.class));

                    } else {
                        Toast.makeText(MainActivity.this, loginModel.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });


        }

    }

}