package com.codepath.instagram.models;

import android.content.Context;

import com.codepath.instagram.helpers.Constants;
import com.codepath.instagram.networking.InstagramApi;
import com.codepath.oauth.OAuthAsyncHttpClient;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/**
 * Created by Lin on 12/1/15.
 */
public class InstagramClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = InstagramApi.class;
    public static final String REST_URL = "https://api.instagram.com/v1/";
    public static final String REST_CONSUMER_KEY = "e05c462ebd86446ea48a5af73769b602";
    public static final String REST_CONSUMER_SECRET = "7f18a14de6c241c2a9ccc9f4a3df4b35";
    public static final String REDIRECT_URI = Constants.REDIRECT_URI;
    public static final String SCOPE = Constants.SCOPE;

    public InstagramClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REDIRECT_URI, SCOPE);
    }

    public void getPopularFeed(JsonHttpResponseHandler handler) {
        String url = "https://api.instagram.com/v1/users/self/feed";
        RequestParams params = new RequestParams();
        client.get(url, params, handler);
    }

    public void getAllComments(String mediaId, JsonHttpResponseHandler handler) {
        String url = "https://api.instagram.com/v1/media/"
                + mediaId + "/comments";
        RequestParams params = new RequestParams();
        client.get(url, params, handler);
    }
}
