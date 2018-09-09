package com.example.user.myapplication.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.myapplication.Model.OwnerInfo;
import com.example.user.myapplication.Model.RepoInfo;
import com.example.user.myapplication.R;

import java.util.ArrayList;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.itemHolder> {

        Context mContext;
        private OwnerInfo ownerInfo;
        private ArrayList<RepoInfo> mItems;

        final int OWNER_INFO = 0;
        final int REPO_INFO=1;

        ViewHolderFactory VHF = new ViewHolderFactory();

        interface ViewHolderSetup{
            void setUp(int position);
        }

        public RepoAdapter(Context context, OwnerInfo oI, ArrayList itemList) {
            mContext = context;
            ownerInfo = oI;
            mItems = itemList;
        }

        @Override
        public itemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return VHF.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(itemHolder holder, final int position) {
            ((ViewHolderSetup)holder).setUp(position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }


        @Override
        public int getItemViewType(int position) {
            if(position == 0){
                return OWNER_INFO;
            }else {
                return REPO_INFO;
            }
        }

        public abstract class itemHolder extends RecyclerView.ViewHolder {
            public itemHolder(View itemView) {
                super(itemView);
            }
        }

        public class ownerItemHolder extends itemHolder implements RepoAdapter.ViewHolderSetup {
            public TextView ownerName;
            public ImageView ownerAvatar;

            public ownerItemHolder(View itemView) {
                super(itemView);
                ownerName = itemView.findViewById(R.id.ownerName);
                ownerAvatar =  itemView.findViewById(R.id.ownerAvatar);
            }

            @Override
            public void setUp(int position) {
                ownerName.setText(ownerInfo.getOwnerName());
                Glide.with(mContext).load(ownerInfo.getOwnerAvatar()).into(ownerAvatar);
            }
        }


        public class repoItemHolder extends itemHolder implements RepoAdapter.ViewHolderSetup {
            public TextView repoName;
            public TextView repoDesc;
            public TextView starCount;

            public repoItemHolder(View itemView) {
                super(itemView);
                repoName = itemView.findViewById(R.id.repoName);
                repoDesc = itemView.findViewById(R.id.repoDesc);
                starCount = itemView.findViewById(R.id.starCount);
            }

            @Override
            public void setUp(int position) {
                RepoInfo RI = mItems.get(position);
                repoName.setText(RI.getRepoName());
                repoDesc.setText(RI.getRepoDesc());
                starCount.setText(RI.getStarCount());
            }
        }

    public class ViewHolderFactory {

        RepoAdapter.itemHolder createViewHolder(ViewGroup parent, int viewType){
            if(viewType == OWNER_INFO){
                View v = LayoutInflater.from(mContext).inflate(R.layout.ownerinfoview, parent, false);
                itemHolder holder = new ownerItemHolder(v);
                return holder;
            }else{
                View v = LayoutInflater.from(mContext).inflate(R.layout.repoinfoview, parent, false);
                itemHolder holder = new repoItemHolder(v);
                return holder;
            }

        }

    }

}
