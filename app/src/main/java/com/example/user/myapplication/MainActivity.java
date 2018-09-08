package com.example.user.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<oneItem> items = new ArrayList();
    Call<ResponseBody> ownerRequest;
    Call<List<repoInfo>> repoRequest;
    RecyclerView repoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repoRecyclerView = findViewById(R.id.repos);


        Uri data = this.getIntent().getData();
        String uri = "";
        if (data != null && data.isHierarchical()) {
            uri = this.getIntent().getDataString();
        }
        String username = uri.split("/")[3];

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).build();
        getGithubInfo service = retrofit.create(getGithubInfo.class);

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
                    oneItem OI = new ownerInfo(ownerName, ownerAvatar);
                    items.add(OI);

                    doRepoRequest();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "데이터 형식 비정상", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "조회 실패", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void doRepoRequest(){
        repoRequest.enqueue(new Callback<List<repoInfo>>() {
            @Override
            public void onResponse(Call<List<repoInfo>> call, Response<List<repoInfo>> response) {
                try {
                    List list = response.body();
                    for (int i = 0; i < list.size(); i++) {
                            items.add((repoInfo)list.get(i));
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "데이터 형식 비정상", Toast.LENGTH_LONG).show();
                }

                reposAdapter mAdapter = new reposAdapter(MainActivity.this, items);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                repoRecyclerView.setAdapter(mAdapter);
                repoRecyclerView.addItemDecoration(new ListPaddingDecoration(MainActivity.this));
                repoRecyclerView.setLayoutManager(mLayoutManager);

            }

            @Override
            public void onFailure(Call<List<repoInfo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "조회 실패", Toast.LENGTH_LONG).show();
            }
        });
        }
    }



