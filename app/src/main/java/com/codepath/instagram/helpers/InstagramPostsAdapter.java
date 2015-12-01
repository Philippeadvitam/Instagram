package com.codepath.instagram.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramPost;

import java.util.ArrayList;

/**
 * Created by Lin on 11/30/15.
 */
public class InstagramPostsAdapter extends RecyclerView.Adapter<InstagramPostsAdapter.PostItemHolder>{
    ArrayList<InstagramPost> posts;

    public InstagramPostsAdapter(ArrayList<InstagramPost> posts) {
        this.posts = posts;
    }

    @Override
    public PostItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_item_post, parent, false);

        // Return a new holder instance
        PostItemHolder itemHolder = new PostItemHolder(contactView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(PostItemHolder holder, int position) {
        InstagramPost post = posts.get(position);
        holder.userName.setText(post.user.fullName);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostItemHolder extends RecyclerView.ViewHolder {
        TextView userName;
        public PostItemHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);

        }
    }
}
