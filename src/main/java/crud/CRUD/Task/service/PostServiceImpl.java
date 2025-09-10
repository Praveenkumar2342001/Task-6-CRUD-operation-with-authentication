package crud.CRUD.Task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crud.CRUD.Task.model.Post;
import crud.CRUD.Task.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts(String username, boolean isAdmin) {
        if (isAdmin) {
            return postRepository.findAll();
        } else {
            return postRepository.findByCreatedBy(username);
        }
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }

    @Override
    public Post updatePost(String id, Post post, String username, boolean isAdmin) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!isAdmin && !existing.getCreatedBy().equals(username)) {
            throw new RuntimeException("Not authorized to update this post");
        }

        existing.setTitle(post.getTitle());
        existing.setContent(post.getContent());
        return postRepository.save(existing);
    }

    @Override
    public void deletePost(String id, String username, boolean isAdmin) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!isAdmin && !existing.getCreatedBy().equals(username)) {
            throw new RuntimeException("Not authorized to delete this post");
        }

        postRepository.deleteById(id);
    }
}
