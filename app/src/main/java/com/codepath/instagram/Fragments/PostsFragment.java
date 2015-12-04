package com.codepath.instagram.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.InstagramPostsAdapter;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramClient;
import com.codepath.instagram.models.InstagramPost;
import com.codepath.instagram.persistence.InstagramClientDatabase;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Lin on 12/2/15.
 */
public class PostsFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    public List<InstagramPost> instagramPosts;
    RecyclerView rvPosts;
    InstagramClientDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        final InstagramClient instagramClient = new InstagramClient(getActivity());
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        setupSearchListener(instagramClient);
        fetchUserPosts(instagramClient);
        return view;
    }

    private void setupSearchListener(final InstagramClient client) {
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0, client);
                //Toast.makeText(getActivity(), "Refreshing", Toast.LENGTH_LONG).show();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchUserPosts(InstagramClient client) {
        client.getPopularFeed(new JsonHttpResponseHandler() {
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
    }

    public void fetchTimelineAsync(int page, InstagramClient client) {
        client.getPopularFeed(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                fetchCode(response);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getActivity(), "onFailure of getPopularFeed", Toast.LENGTH_LONG).show();
            }
        });
    }




    public void fetchCode(JSONObject response) {
        rvPosts = (RecyclerView) getView().findViewById(R.id.rvPosts);
        instagramPosts = Utils.decodePostsFromJsonResponse(response);
        InstagramPostsAdapter adapter = new InstagramPostsAdapter(instagramPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static PostsFragment newInstance() {
        PostsFragment postsFragment = new PostsFragment();
        return postsFragment;
    }

}
