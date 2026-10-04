package data;

import java.util.HashMap;
import java.util.Map;

public final class PostTestData {

    private PostTestData() {
        // Utility class
    }

    public static Map<String, Object> createPost() {
        Map<String, Object> post = new HashMap<>();
        post.put("title", "My REST Assured test post");
        post.put("body", "Testing a post request with REST Assured");
        post.put("userId", 5);
        return post;
    }

    public static Map<String, Object> updatedPost() {
        Map<String, Object> post = new HashMap<>();
        post.put("title", "Updated REST Assured post");
        post.put("body", "Testing a PUT request with REST Assured");
        post.put("userId", 5);
        return post;
    }

    public static Map<String, Object> postTitlePatch() {
        Map<String, Object> patch = new HashMap<>();
        patch.put("title", "Patched REST Assured post");
        return patch;
    }
}