package com.example.user.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class RepoInfo {

    @SerializedName("name")
    String repoName;
    @SerializedName("description")
    String repoDesc;
    @SerializedName("stargazers_count")
    String starCount;

    public RepoInfo(String _repoName, String _repoDesc, String _starCount){
        repoName = _repoName;
        repoDesc = _repoDesc;
        starCount = _starCount;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoDesc() {
        return repoDesc;
    }

    public void setRepoDesc(String repoDesc) {
        this.repoDesc = repoDesc;
    }

    public String getStarCount() {
        return starCount;
    }

    public void setStarCount(String starCount) {
        this.starCount = starCount;
    }

    public static class ComparatorByStarCount implements Comparator<RepoInfo>{
        @Override
        public int compare(RepoInfo o1, RepoInfo o2) {
            return Integer.valueOf(o2.starCount) - Integer.valueOf(o1.starCount);
        }
    }

}
