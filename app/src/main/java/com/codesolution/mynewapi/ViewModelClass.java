package com.codesolution.mynewapi;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codesolution.mynewapi.Models.LoginModel;
import com.codesolution.mynewapi.Models.RegisterModel;
import com.codesolution.mynewapi.Models.UniqueUserNameModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewModelClass extends ViewModel {

    ApiInterface apiInterface = RegisterBaseUrl.getRetrofit().create(ApiInterface.class);

    private MutableLiveData<RegisterModel> registerModelMutableLiveData;
    private MutableLiveData<UniqueUserNameModel> uniqueData;
    private MutableLiveData<LoginModel> matchData;


    public LiveData<UniqueUserNameModel> uniqueDataMethod(Activity activity, String username) {

        uniqueData = new MutableLiveData<UniqueUserNameModel>();

        apiInterface.sendUniqueData(username).enqueue(new Callback<UniqueUserNameModel>() {
            @Override
            public void onResponse(Call<UniqueUserNameModel> call, Response<UniqueUserNameModel> response) {
                if (response.body() != null) {
                    uniqueData.postValue(response.body());
                } else {
                    Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UniqueUserNameModel> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return uniqueData;
    }


    public LiveData<RegisterModel> registerModelLiveData(Activity activity, RequestBody username, RequestBody password, RequestBody joiningDate,
                                                         RequestBody joiningTime, RequestBody profession, MultipartBody.Part image)
    {
        registerModelMutableLiveData = new MutableLiveData<RegisterModel>();

        apiInterface.RegisterUser(username, password, joiningDate, joiningTime, profession, image).enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (response.body()!=null)
                {
                    registerModelMutableLiveData.postValue(response.body());
                }else {
                    Toast.makeText(activity, "null"+response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(activity, "null"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        
        return  registerModelMutableLiveData;
    }

    public LiveData<LoginModel> matchDataMethod(Activity activity, String username, String password) {
        matchData = new MutableLiveData<LoginModel>();

        apiInterface.matchData(username, password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null) {
                    matchData.postValue(response.body());
                } else {
                    Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return matchData;
    }

}
