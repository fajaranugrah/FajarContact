package com.example.fajarcontact.Connection;

import com.example.fajarcontact.Model.Contact;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/contact")
    Call<Contact> getContact(
            @Query("q") String userSearch,
            @Query("page") String page
    );

    @POST("/contact")
    Call<Contact> addContact(
            @Body RequestBody body
    );

    @PUT("/contact")
    Call<Contact> updateContact(
            @Body RequestBody body
    );

    @DELETE("/contact")
    Call<Contact> deleteContact(
            @Body RequestBody body
    );
}
