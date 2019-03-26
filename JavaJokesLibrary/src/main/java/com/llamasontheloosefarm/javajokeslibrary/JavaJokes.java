package com.llamasontheloosefarm.javajokeslibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class JavaJokes {


    private String joke1 = "Why did the chicken cross the road.... Why to get to the other side of course";
    private String joke2 = "A horse walks into a bar. Steps up to the bartender and says... 'Why the long face..'";
    private String joke3 = "What is a pirates favorite letter? ...... RRRRRRRRR";
    private Random randomGenerator;
    private ArrayList<String> jokes;

    public JavaJokes() {
        randomGenerator = new Random();
        jokes = new ArrayList<>(Arrays.asList(joke1, joke2, joke3));
    }

    public String getJoke() {
        int index = randomGenerator.nextInt(jokes.size());
        String joke = jokes.get(index);
        return joke;
    }

}
