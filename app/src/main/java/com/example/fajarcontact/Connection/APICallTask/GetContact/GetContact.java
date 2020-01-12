package com.example.fajarcontact.Connection.APICallTask.GetContact;

import android.app.Activity;

import com.example.fajarcontact.Connection.APICallTask.ApiCalling;
import com.example.fajarcontact.Connection.APIClient;
import com.example.fajarcontact.Connection.APIInterface;
import com.example.fajarcontact.Model.Contact;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class GetContact extends ApiCalling {

    Activity activity;
    Contact allContact;
    List<Contact> items = new ArrayList<>();
    int totalPage;

    public GetContact(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void callApi(String... params){

        //default limit of 10 hits per minute
        String user = params[0];
        String page = params[1];

        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getContact(user, page)
                    .enqueue(new Callback<Contact>() {
                        @Override
                        public void onResponse(Call<Contact> call, Response<Contact> response) {
                            switch (response.code()){
                                case 200:
                                    try {
                                        allContact = response.body();
                                        OnApiResult(1, "");
                                    } catch (Exception convert){
                                        convert.printStackTrace();
                                        OnApiResult( 2, "");
                                    }
                                    break;
                                case 403:
                                    OnApiResult(2, response.message());
                                    break;
                                default:
                                    OnApiResult(3, response.message());
                                    break;
                            }

                        }

                        @Override
                        public void onFailure(Call<Contact> call, Throwable t) {
                            call.cancel();
                            OnApiResult(4, t.getMessage());
                        }
                    });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public abstract void OnApiResult(int statusCode, String failReason);

    public List<Contact> getItems() {
        return this.items;
    }

    public int getTotalPage() {
        return this.totalPage;
    }
}