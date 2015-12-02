package com.codepath.instagram.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.InstagramPostsAdapter;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramClient;
import com.codepath.instagram.models.InstagramPost;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public List<InstagramPost> instagramPosts;
    RecyclerView rvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        InstagramClient.getPopularFeed(new JsonHttpResponseHandler() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchCode(JSONObject response) {
        rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        instagramPosts = Utils.decodePostsFromJsonResponse(response);
        InstagramPostsAdapter adapter = new InstagramPostsAdapter(instagramPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
    }

    public void clickMore(View view) {
        ImageView ivImage = (ImageView) findViewById(R.id.image);

        Uri bmpUri = getLocalBitmapUri(ivImage);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            Toast.makeText(HomeActivity.this, "error sharing image",  Toast.LENGTH_LONG).show();
        }
    }

    private Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;

    }

    public void clickAllComment(View view) {
        InstagramPost post = InstagramPostsAdapter.getCurrentPost();
        Intent intent = new Intent(HomeActivity.this, CommentActivity.class);
        intent.putExtra("mediaId", post.mediaId);
        startActivity(intent);
    }
}
