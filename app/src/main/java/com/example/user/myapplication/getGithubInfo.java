package com.example.user.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface getGithubInfo {

        @GET("users/{username}")
        Call<ResponseBody> showOwner(@Path("username") String username);

        @GET("users/{username}/repos")
        Call<ResponseBody> listRepos(@Path("username") String username);

}
