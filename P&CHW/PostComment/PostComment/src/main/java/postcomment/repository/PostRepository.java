package postcomment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import postcomment.domain.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
