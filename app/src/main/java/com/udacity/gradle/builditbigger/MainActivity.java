package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.llamasontheloosefarm.javajokeslibrary.JavaJokes;
import com.llamasontheloosefarm.androidjokelibrary.JokeActivity;
import com.udacity.gradle.builditbigger.backend.myApi.*;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    JavaJokes jokes;
    OnTaskCompleted<String> mListener;  // TODO: how do I instantiate this?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jokes = new JavaJokes();


        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void tellJoke(View view) {

        mListener = new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompeted(String joke) {
                launchAndroidJokeLibraryActivity(joke);
            }
        };

        // Playing with backend
        new EndpointsAsyncTask(mListener).execute();

    }


    private void launchAndroidJokeLibraryActivity(String joke) {

        Context context = MainActivity.this;
        Class dest = JokeActivity.class;
        Intent jokeIntent = new Intent(context, dest);
        String oneJoke = jokes.getJoke();
        jokeIntent.putExtra("jokeString", oneJoke);

        startActivity(jokeIntent);
    }

}
