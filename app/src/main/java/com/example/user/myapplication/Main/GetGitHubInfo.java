package com.example.user.myapplication.Main;

import com.example.user.myapplication.Model.RepoInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetGitHubInfo {

        @GET("users/{username}")
        Call<ResponseBody> showOwner(@Path("username") String username);

        @GET("users/{username}/repos")
        Call<List<RepoInfo>> listRepos(@Path("username") String username);

}
