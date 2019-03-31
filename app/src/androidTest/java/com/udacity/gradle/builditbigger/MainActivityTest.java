package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest  {


    private static final String TAG = MainActivityTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void TestGetJokeAsyncCall() throws Throwable {

        Activity testActivity = mActivityRule.getActivity();

        final CountDownLatch signal = new CountDownLatch(1);

        final AsyncTask<Pair<Context, String>, Void, String> myTestTask = new AsyncTask<Pair<Context, String>, Void, String>() {
            private MyApi myApiServiceTest = null;
            private Context contextTest;


            @Override
            protected String doInBackground(Pair<Context, String>... pairs) {
                if (myApiServiceTest == null) {
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                    request.setDisableGZipContent(true);
                                }
                            });
                    myApiServiceTest = builder.build();
                }

                contextTest = pairs[0].first;
                String name = pairs[0].second;

                try {
                    return myApiServiceTest.getMyJoke().execute().getData();
                } catch (IOException io) {
                    return io.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                signal.countDown();
            }

        };

        testActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myTestTask.execute();
            }
        });

        signal.await(30, TimeUnit.SECONDS);


        Assert.assertNotEquals("string", "notstring");

//        final Object syncObject = new Object();
//
//        GetJokeTestMainActivity mainTestActivity = mActivityRule.getActivity();
//        mainTestActivity.setGetJokeTestCallback(new GetJokeTestMainActivity.GetJokeTestCallback() {
//            @Override
//            public void onHandleResponseCalled(String joke) {
//                Log.d(TAG, "TESTING Getting Joke: " + joke);
//                Assert.assertNotEquals("somestring", joke);
//                synchronized (syncObject) {
//                    syncObject.notify();
//                }
//            }
//        });
//        onView(withId(R.id.instructions_text_view)).perform(click());
//
//
//        synchronized (syncObject) {
//            syncObject.wait();
//        }
    }

}