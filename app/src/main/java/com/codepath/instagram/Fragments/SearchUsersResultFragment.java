package com.codepath.instagram.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.helpers.InstagramPostsAdapter;
import com.codepath.instagram.helpers.SearchUserResultsAdapter;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramClient;
import com.codepath.instagram.models.InstagramUser;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Lin on 12/2/15.
 */
public class SearchUsersResultFragment extends Fragment {
    RecyclerView rvPosts;
    List<InstagramUser> instagramUsers;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getActivity(),"search_icon",Toast.LENGTH_LONG).show();
                InstagramClient client = MainApplication.getRestClient();
                client.getSearchUserResults("username", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        decodeSearch(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Toast.makeText(getActivity(), "onFailure of getPopularFeed", Toast.LENGTH_LONG).show();
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void decodeSearch(JSONObject response) {
        rvPosts = (RecyclerView) getView().findViewById(R.id.rvPosts);
        instagramUsers = Utils.decodeUsersFromJsonResponse(response);
        SearchUserResultsAdapter adapter = new SearchUserResultsAdapter(instagramUsers);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public static Fragment newInstance() {
        SearchUsersResultFragment searchFragment = new SearchUsersResultFragment();
        return searchFragment;
    }
}
