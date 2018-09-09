package com.example.user.myapplication.Main;

import android.content.Context;
import android.content.Intent;

import com.example.user.myapplication.Model.OwnerInfo;
import com.example.user.myapplication.Model.RepoInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements Contract.PresenterMethod{

    Contract.ActivityMethod mContext;

    String username;
    ArrayList<RepoInfo> items = new ArrayList();
    Call<ResponseBody> ownerRequest;
    Call<List<RepoInfo>> repoRequest;
    String githubAddr = "https://api.github.com/";
    OwnerInfo oI;

    public MainPresenter(Context context){
       mContext =  (Contract.ActivityMethod)context;
    }

    @Override
    public void getDeepLinkInfo(Intent intent){
        String uri = intent.getDataString();
        if (uri != null) {
           username = uri.split("/")[3];
           mContext.proceedOrNot(true);
        }else{
           mContext.proceedOrNot(false);
        }
    }

    @Override
    public void fetchGithubInfo() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(githubAddr).addConverterFactory(GsonConverterFactory.create()).build();
        GetGitHubInfo service = retrofit.create(GetGitHubInfo.class);
        ownerRequest = service.showOwner(username);
        repoRequest = service.listRepos(username);

        ownerRequest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    JSONObject ownerInfo = new JSONObject(res);
                    String ownerName = ownerInfo.getString("login");
                    String ownerAvatar = ownerInfo.getString("avatar_url");
                    oI = new OwnerInfo(ownerName, ownerAvatar);

                    doRepoRequest();
                } catch (Exception e) {
                    mContext.showExceptionToast("데이터 형식 비정상");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mContext.showExceptionToast("조회 실패");
            }
        });
    }

    public void doRepoRequest(){
        repoRequest.enqueue(new Callback<List<RepoInfo>>() {
            @Override
            public void onResponse(Call<List<RepoInfo>> call, Response<List<RepoInfo>> response) {
                try {
                    List list = response.body();
                    for (int i = 0; i < list.size(); i++) {
                        items.add((RepoInfo)list.get(i));
                    }
                } catch (Exception e) {
                    mContext.showExceptionToast("데이터 형식 비정상");
                }

                Collections.sort(items, new RepoInfo.ComparatorByStarCount());
                mContext.showList(oI, items);
            }

            @Override
            public void onFailure(Call<List<RepoInfo>> call, Throwable t) {
                mContext.showExceptionToast("조회 실패");
            }
        });
    }
}
