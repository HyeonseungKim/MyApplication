package com.example.user.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class reposAdapter extends RecyclerView.Adapter<reposAdapter.itemHolder> {

        private ArrayList<oneItem> mItems;
        Context mContext;

        public reposAdapter(Context context, ArrayList itemList) {
            mContext = context;
            mItems = itemList;
        }

        @Override
        public itemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType == 0){
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ownerinfoview, parent, false);
                itemHolder holder = new ownerItemHolder(v);
                return holder;
            }else{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repoinfoview, parent, false);
                itemHolder holder = new repoItemHolder(v);
                return holder;
            }
        }

        @Override
        public void onBindViewHolder(itemHolder holder, final int position) {

            if(position == 0){

                ownerInfo OI = ((ownerInfo)mItems.get(0));
                ownerItemHolder OIH = (ownerItemHolder)holder;

                OIH.ownerName.setText(OI.ownerName);
                Glide.with(mContext).load(OI.ownerAvatar).into(OIH.ownerAvatar);

            }else{

                repoInfo RI = ((repoInfo)mItems.get(position));
                repoItemHolder RIH = (repoItemHolder)holder;

                RIH.repoName.setText(RI.repoName);
                RIH.repoDesc.setText(RI.repoDesc);
                RIH.starCount.setText(RI.starCount);

            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }


        @Override
        public int getItemViewType(int position) {
            if(position == 0){
                return 0;
            }else {
                return 1;
            }
        }


        public abstract class itemHolder extends RecyclerView.ViewHolder {
            public itemHolder(View itemView) { super(itemView); }
        }


        public class ownerItemHolder extends itemHolder {
            public TextView ownerName;
            public ImageView ownerAvatar;

            public ownerItemHolder(View itemView) {
                super(itemView);
                ownerName = itemView.findViewById(R.id.ownerName);
                ownerAvatar =  itemView.findViewById(R.id.ownerAvatar);
            }
        }


        public class repoItemHolder extends itemHolder {
            public TextView repoName;
            public TextView repoDesc;
            public TextView starCount;

            public repoItemHolder(View itemView) {
                super(itemView);
                repoName = itemView.findViewById(R.id.repoName);
                repoDesc = itemView.findViewById(R.id.repoDesc);
                starCount = itemView.findViewById(R.id.starCount);
            }
        }
}
