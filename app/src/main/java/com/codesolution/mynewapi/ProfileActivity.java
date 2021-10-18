package com.codesolution.mynewapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.codesolution.mynewapi.Models.RegisterModel;
import com.codesolution.mynewapi.databinding.ActivityPickerBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity {
    ActivityPickerBinding binding;

    String[] cTime={"Select Course", "BCA", "MCA","MBA", "BBA"};
    int pos;
    String CourseTime;
    private final int REQ = 1;
    private Bitmap bitmap;
    int permissionCode = 123;
    private List list = new ArrayList();
    private int index;

    private String str_picture;

    private RequestBody rb_username, rb_password, rb_date, rb_time;
    private String userNameStr, passwordStr, dateStr, timeStr;

    private ViewModelClass viewModelClass;
    private RequestBody rb_spinner;
    private MultipartBody.Part rb_str_picture;

    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.INTERNET};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                ;
            requestPermissions(permission, permissionCode);
        }

        list.add("Choose from below :");
        list.add("BCA");
        list.add("MCA");
        list.add("BBA");
        list.add("MBA");


        ArrayAdapter<String> time=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        binding.spTime.setAdapter(time);

        binding.spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                index = binding.spTime.getSelectedItemPosition();
                if (index == 0) {

                } else if (index == 1) {
                    App.getSingleton().setSpinner("BCA");
                } else if (index == 2) {
                    App.getSingleton().setSpinner("MCA");
                } else if (index == 3) {
                    App.getSingleton().setSpinner("BBA");
                }
                else if (index == 4) {
                    App.getSingleton().setSpinner("MBA");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadData();

            }
        });

        userNameStr = App.getSingleton().getUserName();
        passwordStr = App.getSingleton().getPassword();
        dateStr = App.getSingleton().getDate();
        timeStr = App.getSingleton().getTime();


        binding.chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }


    private void uploadData() {

        if(binding.spTime.getSelectedItemPosition() == 0)
        {
            Toast.makeText(ProfileActivity.this, "Kindly Select Course", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (bitmap == null)
        {
            Toast.makeText(ProfileActivity.this, "Please Upload Image", Toast.LENGTH_SHORT).show();

        }
        else
        {



          App.getSingleton().setImage(str_picture);

            viewModelClass = ViewModelProviders.of(this).get(ViewModelClass.class);

            rb_username =RequestBody.create(MediaType.parse("text/plain"), userNameStr);
            rb_password=RequestBody.create(MediaType.parse("text/plain"), passwordStr);
            rb_date =RequestBody.create(MediaType.parse("text/plain"), dateStr);
            rb_time =RequestBody.create(MediaType.parse("text/plain"), timeStr);

         rb_spinner =RequestBody.create(MediaType.parse("text/plain"), App.getSingleton().getSpinner());


            if (str_picture != null)
            {

                File file  = new File(str_picture);
                final RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                rb_str_picture = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            }

            viewModelClass.registerModelLiveData(ProfileActivity.this, rb_username, rb_password, rb_date, rb_time, rb_spinner, rb_str_picture).observe(this, new Observer<RegisterModel>() {
                @Override
                public void onChanged(RegisterModel registerModel) {
                    if (registerModel.getSuccess().equalsIgnoreCase("1"))
                    {
                        startActivity(new Intent(ProfileActivity.this, CongActivity.class));
                        Toast.makeText(ProfileActivity.this, registerModel.getMessage(), Toast.LENGTH_LONG).show();

                    }
                    else if (registerModel.getSuccess().equalsIgnoreCase("0")){

                        Toast.makeText(ProfileActivity.this, registerModel.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == ProfileActivity.this.RESULT_OK)
        {
            Uri uri = data.getData();

            str_picture = getPathFromURI(uri);
            Toast.makeText(ProfileActivity.this, "" + str_picture, Toast.LENGTH_SHORT).show();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(ProfileActivity.this.getContentResolver(), uri);
            } catch (IOException e) {

            }

            binding.image.setImageBitmap(bitmap);

        }
    }
    private String getPathFromURI(Uri selected) {

        String path;
        Cursor cursor = ProfileActivity.this.getContentResolver().query(selected, null, null, null, null);
        if (cursor == null)
            path = selected.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path = cursor.getString(index);
        }
        if (cursor != null)
            cursor.close();
        return path;
    }

}