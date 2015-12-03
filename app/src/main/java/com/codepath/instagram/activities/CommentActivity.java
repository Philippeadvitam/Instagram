package com.codepath.instagram.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.InstagramCommentsAdapter;
import com.codepath.instagram.helpers.InstagramPostsAdapter;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramClient;
import com.codepath.instagram.models.InstagramComment;
import com.codepath.instagram.models.InstagramPost;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class CommentActivity extends AppCompatActivity {
    RecyclerView rvComments;
    String mediaId;
    List<InstagramComment> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mediaId = getIntent().getStringExtra("mediaId");
        InstagramClient instagramClient = new InstagramClient(CommentActivity.this);
        instagramClient.getAllComments(mediaId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                fetchCode(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });
    }

    private void fetchCode(JSONObject response) {
        rvComments = (RecyclerView) findViewById(R.id.rvPosts);
        comments = Utils.decodeCommentsFromJsonResponse(response);
        InstagramCommentsAdapter adapter = new InstagramCommentsAdapter(comments);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
    }
}
