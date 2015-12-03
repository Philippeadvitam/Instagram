package com.codepath.instagram.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.InstagramPostsAdapter;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramClient;
import com.codepath.instagram.models.InstagramPost;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Lin on 12/2/15.
 */
public class PostsFragment extends Fragment {
    public List<InstagramPost> instagramPosts;
    RecyclerView rvPosts;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        InstagramClient instagramClient = new InstagramClient(getActivity());
        instagramClient.getPopularFeed(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                fetchCode(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getActivity(), "onFailure of getPopularFeed", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    public void fetchCode(JSONObject response) {
        rvPosts = (RecyclerView) getView().findViewById(R.id.rvPosts);
        instagramPosts = Utils.decodePostsFromJsonResponse(response);
        InstagramPostsAdapter adapter = new InstagramPostsAdapter(instagramPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static PostsFragment newInstance(int page) {
        PostsFragment postsFragment = new PostsFragment();
        return postsFragment;
    }

}
