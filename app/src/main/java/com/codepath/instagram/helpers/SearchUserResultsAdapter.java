package com.codepath.instagram.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramUser;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lin on 12/2/15.
 */
public class SearchUserResultsAdapter extends RecyclerView.Adapter<SearchUserResultsAdapter.PostItemHolder> {
    List<InstagramUser> instagramUsers;
    Context context;

    public SearchUserResultsAdapter(List<InstagramUser> instagramUsers) {
        this.instagramUsers = instagramUsers;
    }

    @Override
    public PostItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_item_post, parent, false);

        // Return a new holder instance
        PostItemHolder itemHolder = new PostItemHolder(contactView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(PostItemHolder holder, int position) {
        InstagramUser user = instagramUsers.get(position);
        holder.searchUsername.setText(user.userName);
        Picasso.with(context).load(user.profilePictureUrl).into(holder.searchAvatar);

    }
    @Override
    public int getItemCount() {
        return instagramUsers.size();
    }

    public static class PostItemHolder extends RecyclerView.ViewHolder {
        TextView searchUsername;
        ImageView searchAvatar;

        public PostItemHolder(View itemView) {
            super(itemView);
            searchUsername = (TextView) itemView.findViewById(R.id.userSearchUsername);
            searchAvatar = (ImageView) itemView.findViewById(R.id.userSearchAvatar);

        }
    }
}
