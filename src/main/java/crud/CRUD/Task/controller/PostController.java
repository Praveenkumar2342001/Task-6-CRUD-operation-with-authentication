package crud.CRUD.Task.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import crud.CRUD.Task.model.Post;
import crud.CRUD.Task.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

   @GetMapping
public List<Post> getAllPosts(Authentication auth) {
    String username = "anonymous";
    boolean isAdmin = false;

    if (auth != null) {
        username = auth.getName();
        isAdmin = auth.getAuthorities().stream()
                      .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    }

    return postService.getAllPosts(username, isAdmin);
}

  @PostMapping
public Post createPost(@RequestBody Post post, Authentication auth) {
    if (auth != null) {
        post.setCreatedBy(auth.getName());
    } else {
        post.setCreatedBy("anonymous");
    }
    return postService.createPost(post);
}

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @PutMapping("/{id}")
public Post updatePost(@PathVariable String id, @RequestBody Post post, Authentication auth) {
    if (auth == null) {
        throw new RuntimeException("Unauthorized: Please provide credentials");
    }

    String username = auth.getName();
    boolean isAdmin = auth.getAuthorities().stream()
                          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

    return postService.updatePost(id, post, username, isAdmin);
}

    

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable String id, Authentication auth) {
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                              .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        postService.deletePost(id, username, isAdmin);
        return "Post deleted successfully";
    }
}