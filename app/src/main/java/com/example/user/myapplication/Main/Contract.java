package com.example.user.myapplication.Main;

import android.content.Intent;

import com.example.user.myapplication.Model.OwnerInfo;
import com.example.user.myapplication.Model.RepoInfo;

import java.util.ArrayList;

public class Contract {

    interface PresenterMethod{
        void getDeepLinkInfo(Intent intent);
        void fetchGithubInfo();
    }

    interface ActivityMethod{
        void showExceptionToast(String message);
        void showList(OwnerInfo oI, ArrayList<RepoInfo> arrayList);
        void proceedOrNot(boolean bool);
    }

}
