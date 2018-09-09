package com.example.user.myapplication.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user.myapplication.Design.ListPaddingDecoration;
import com.example.user.myapplication.Model.OwnerInfo;
import com.example.user.myapplication.R;
import com.example.user.myapplication.Model.RepoInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Contract.ActivityMethod{

    MainPresenter mPresenter = new MainPresenter(this);
    RecyclerView repoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repoRecyclerView = findViewById(R.id.repos);

        mPresenter.getDeepLinkInfo(getIntent());
    }


    @Override
    public void showExceptionToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showList(OwnerInfo oI, ArrayList<RepoInfo> items) {
        RepoAdapter mAdapter = new RepoAdapter(this, oI, items);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        repoRecyclerView.addItemDecoration(new ListPaddingDecoration(this));
        repoRecyclerView.setLayoutManager(mLayoutManager);
        repoRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void proceedOrNot(boolean bool) {
        if(bool==true) {
            mPresenter.fetchGithubInfo();
        }else{
            Toast.makeText(this, "DeepLink로 들어오지 않아 앱을 종료합니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}



