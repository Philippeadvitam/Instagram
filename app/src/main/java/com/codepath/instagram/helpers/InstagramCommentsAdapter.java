package com.codepath.instagram.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramComment;
import com.codepath.instagram.models.InstagramPost;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lin on 12/1/15.
 */
public class InstagramCommentsAdapter extends RecyclerView.Adapter<InstagramCommentsAdapter.PostItemHolder> {
    Context context;
    List<InstagramComment> comments;
    public InstagramCommentsAdapter(List<InstagramComment> comments) {
        this.comments = comments;
    }
    @Override
    public InstagramCommentsAdapter.PostItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_item_comment, parent, false);

        // Return a new holder instance
        PostItemHolder itemHolder = new PostItemHolder(contactView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(InstagramCommentsAdapter.PostItemHolder holder, int position) {
        InstagramComment comment = comments.get(position);
        holder.commenter.setText(comment.user.userName + " " + comment.text);
        Picasso.with(context).load(comment.user.profilePictureUrl).into(holder.avatar);
        //holder.commentContent.setText(comment.text);
        holder.commentTime.setText(DateUtils.getRelativeTimeSpanString(comment.createdTime * 1000));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class PostItemHolder extends RecyclerView.ViewHolder {
        TextView commenter;
        ImageView avatar;
        TextView commentTime;
        TextView commentContent;
        public PostItemHolder(View itemView) {
            super(itemView);
            commenter = (TextView) itemView.findViewById(R.id.commenter);
            avatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            commentTime = (TextView) itemView.findViewById(R.id.comment_time);
            commentContent = (TextView) itemView.findViewById(R.id.comment_content);
        }
    }

}
