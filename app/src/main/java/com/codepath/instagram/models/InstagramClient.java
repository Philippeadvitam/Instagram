package com.codepath.instagram.models;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Lin on 12/1/15.
 */
public class InstagramClient {

    public static void getPopularFeed(JsonHttpResponseHandler handler) {
        String url = "https://api.instagram.com/v1/media/popular?client_id=e05c462ebd86446ea48a5af73769b602";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(url, params, handler);
    }

    public static void getAllComments(String mediaId, JsonHttpResponseHandler handler) {
        String url = "https://api.instagram.com/v1/media/"
                + mediaId + "/comments?client_id=e05c462ebd86446ea48a5af73769b602";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(url, params, handler);
    }
}
