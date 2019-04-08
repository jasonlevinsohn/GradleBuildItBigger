package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

import com.llamasontheloosefarm.javajokeslibrary.JavaJokes;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    JavaJokes theJokes = new JavaJokes();

    @ApiMethod(name = "getMyJoke")
    public MyBean getAJoke() {
        String joke = theJokes.getJoke();
        MyBean response = new MyBean();

        response.setData(joke);

        return response;

    }

}
