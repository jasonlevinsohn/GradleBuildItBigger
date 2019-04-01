package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private MyApi myApiService = null;
    private OnTaskCompleted<String> listener;

    public EndpointsAsyncTask(OnTaskCompleted<String> listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                        request.setDisableGZipContent(true);
                    }
                });
            myApiService = builder.build();
        }

        try {
            return myApiService.getMyJoke().execute().getData();
        } catch (IOException io) {
            return io.getMessage();
        }
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
//        launchAndroidJokeLibraryActivity(s);
        if (s != null && listener != null) {
            listener.onTaskCompeted(s);
        }
    }
}
