package postcomment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import postcomment.domain.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}