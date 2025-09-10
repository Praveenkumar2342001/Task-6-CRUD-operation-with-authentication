package crud.CRUD.Task.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import crud.CRUD.Task.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByCreatedBy(String createdBy);
}
