package com.codesolution.mynewapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private ImageView image_viewer;
    private TextView txt_name, txt_password, txt_date_, txt_time_, txt_spinner;
    private String str_image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        findId();
        setData();

    }

    private void setData() {

        str_image = App.getSingleton().getImage();
        txt_name.setText(App.getSingleton().getUserName());
        txt_password.setText(App.getSingleton().getPassword());
        txt_date_.setText(App.getSingleton().getDateLogin());
        txt_time_.setText(App.getSingleton().getTime());
        txt_spinner.setText(App.getSingleton().getSpinner());

    }

    private void findId() {
        image_viewer = findViewById(R.id.image_viewer_);
        txt_name = findViewById(R.id.txt_name);
        txt_password = findViewById(R.id.txt_password);
        txt_date_ = findViewById(R.id.txt_date_);
        txt_time_ = findViewById(R.id.txt_time_);
        txt_spinner = findViewById(R.id.txt_spinner);
    }


}