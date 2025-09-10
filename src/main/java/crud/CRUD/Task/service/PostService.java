package crud.CRUD.Task.service;

import java.util.List;
import java.util.Optional;
import crud.CRUD.Task.model.Post;

public interface PostService {
    List<Post> getAllPosts(String username, boolean isAdmin);
    Post createPost(Post post);
    Optional<Post> getPostById(String id);
    Post updatePost(String id, Post post, String username, boolean isAdmin);
    void deletePost(String id, String username, boolean isAdmin);
}
