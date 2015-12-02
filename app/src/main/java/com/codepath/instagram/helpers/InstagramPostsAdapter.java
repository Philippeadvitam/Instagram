package com.codepath.instagram.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramPost;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lin on 11/30/15.
 */
public class InstagramPostsAdapter extends RecyclerView.Adapter<InstagramPostsAdapter.PostItemHolder>{
    List<InstagramPost> posts;
    static Context context;
    public static InstagramPost curPost;
    public InstagramPostsAdapter(List<InstagramPost> posts) {
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
        curPost = post;
        holder.userName.setText(post.user.userName);
        Picasso.with(context).load(post.user.profilePictureUrl).into(holder.profile_picture);
        Picasso.with(context).load(post.image.imageUrl)
                .resize(0, 1000)
                .into(holder.image);
        holder.date.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000));
        holder.likes.setText(post.likesCount + " likes");
        holder.caption.setText(post.caption);
        holder.llComments.removeAllViews();
        for (int i = 0; i < 2; ++i) {
            TextView commentView = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_item_text_comment, holder.llComments, false);
            commentView.setText(post.comments.get(i).text);
            holder.llComments.addView(commentView);
        }
        holder.comment_count.setText("View all " + post.commentsCount + "comments");

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostItemHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView image;
        TextView date;
        TextView likes;
        TextView caption;
        RoundedImageView profile_picture;
        LinearLayout llComments;
        TextView comment_count;

        public PostItemHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            image = (ImageView) itemView.findViewById(R.id.image);
            date = (TextView) itemView.findViewById(R.id.date);
            likes = (TextView) itemView.findViewById(R.id.likes);
            caption = (TextView) itemView.findViewById(R.id.caption);
            profile_picture = (RoundedImageView) itemView.findViewById(R.id.profile_picture);
            llComments = (LinearLayout) itemView.findViewById(R.id.llComments);
            comment_count = (TextView) itemView.findViewById(R.id.comment_count);
        }
    }

    public static InstagramPost getCurrentPost() {
        return curPost;
    }
}
