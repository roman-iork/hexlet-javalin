package org.example;

import java.util.HashMap;
import java.util.Map;

public class Data {
    public static Map<Integer, String> users = new HashMap<>();
    public static Map<Integer, String> posts = new HashMap<>();
    public static Map<Integer, Integer> usersPosts = new HashMap<>();

    Data() {
        getData();
    }

    public static void getData() {
        users.put(1, "Ali");
        users.put(2, "Mike");
        users.put(3, "Jey");

        posts.put(11, "So we go");
        posts.put(12, "That's right");
        posts.put(13, "Deal");

        usersPosts.put(1, 13);
        usersPosts.put(2, 11);
        usersPosts.put(3, 12);
    }
}
