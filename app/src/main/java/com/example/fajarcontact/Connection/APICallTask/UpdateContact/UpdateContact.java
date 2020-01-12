package com.example.fajarcontact.Connection.APICallTask.UpdateContact;

import android.app.Activity;
import android.util.ArrayMap;

import com.example.fajarcontact.Connection.APICallTask.ApiCalling;
import com.example.fajarcontact.Connection.APIClient;
import com.example.fajarcontact.Connection.APIInterface;
import com.example.fajarcontact.Model.Contact;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class UpdateContact extends ApiCalling {

    Activity activity;
    Contact allContact;
    List<Contact> items = new ArrayList<>();
    int totalPage;

    public UpdateContact(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void callApi(Contact... params){

        //default limit of 10 hits per minute
        Map<String, Object> jsonParams = new ArrayMap<>();
        //put something inside the map, could be null
        jsonParams.put("contact", params[0]);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .updateContact(body)
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