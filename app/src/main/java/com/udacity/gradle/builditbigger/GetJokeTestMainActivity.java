package com.udacity.gradle.builditbigger;

public class GetJokeTestMainActivity extends MainActivity {

    private GetJokeTestCallback mCallBack;

    public void setGetJokeTestCallback(GetJokeTestCallback jokeCallback) {
        mCallBack = jokeCallback;
    }

    public interface GetJokeTestCallback {
        void onHandleResponseCalled(String joke);
    }

    @Override
    public void handleGetJokeResponse(String s) {
        mCallBack.onHandleResponseCalled(s);
    }
}
