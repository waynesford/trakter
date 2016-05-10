package com.example.rapunzel.trakter.ui;

import com.example.rapunzel.trakter.R;
import com.example.rapunzel.trakter.data.viewmodels.FollowedUserRowVM;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FollowedUserVH> {
    private List<FollowedUserRowVM> mFollowedUsersRowModels = Collections.emptyList();

    static class FollowedUserVH extends RecyclerView.ViewHolder {

        private final FollowedUsersRowView mView;

        private FollowedUserRowVM mFollowedUser;

        public FollowedUserVH(FollowedUsersRowView view) {
            super(view);
            mView = view;
        }

        public void bind(FollowedUserRowVM followedUserRowVM) {
            mFollowedUser = followedUserRowVM;
            mView.bind(mFollowedUser);
        }
    }

    @Override
    public FollowedUserVH onCreateViewHolder(ViewGroup parent,
            int viewType) {
        FollowedUsersRowView rowView = (FollowedUsersRowView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.followed_user_row, parent, false);
        return new FollowedUserVH(rowView);
    }

    @Override
    public void onBindViewHolder(FollowedUserVH holder, int position) {
        holder.bind(mFollowedUsersRowModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mFollowedUsersRowModels.size();
    }

    public void setData(List<FollowedUserRowVM> followedUsers){
        mFollowedUsersRowModels = followedUsers;
    }
}
