package com.codepath.instagram.helpers;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramPost;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Lin on 11/30/15.
 */
public class InstagramPostsAdapter extends RecyclerView.Adapter<InstagramPostsAdapter.PostItemHolder>{
    ArrayList<InstagramPost> posts;
    static Context context;

    public InstagramPostsAdapter(ArrayList<InstagramPost> posts) {
        this.posts = posts;
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
        InstagramPost post = posts.get(position);
        holder.userName.setText(post.user.userName);
        Picasso.with(context).load(post.user.profilePictureUrl).into(holder.avatar);
        Picasso.with(context).load(post.image.imageUrl)
                .placeholder(R.drawable.gray_rectangle)
                .resize(1000, 0)
                .into(holder.image);
        holder.date.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000));
        holder.likes.setText(post.likesCount + " likes");
        holder.caption.setText(post.caption);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostItemHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView avatar;
        ImageView image;
        TextView date;
        TextView likes;
        TextView caption;
        public PostItemHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            image = (ImageView) itemView.findViewById(R.id.image);
            date = (TextView) itemView.findViewById(R.id.date);
            likes = (TextView) itemView.findViewById(R.id.likes);
            caption = (TextView) itemView.findViewById(R.id.caption);
        }
    }
}
