package com.udacity.gradle.builditbigger;

public interface OnTaskCompleted<String> {
    void onTaskCompeted(String joke);
}
