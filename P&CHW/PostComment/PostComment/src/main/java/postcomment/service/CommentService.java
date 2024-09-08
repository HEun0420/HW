package postcomment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import postcomment.domain.dto.CommentDTO;
import postcomment.domain.entity.Comment;
import postcomment.global.CommentNotFoundException;
import postcomment.global.PostNotFoundException;
import postcomment.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    // 등록
    @Transactional
    public void registComment(CommentDTO newComment) {
        Comment comment = Comment.builder()
                .CommentId(newComment.getCommentId())
                .author(newComment.getAuthor())
                .text(newComment.getText())
                .build();

        commentRepository.save(comment);
    }


//     단일조회
    public CommentDTO getCommentByCommentId(long commentId) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다: " + commentId));

        return CommentDTO.builder()
            .commentId(comment.getCommentId())
            .author(comment.getAuthor())
            .text(comment.getText())
            .build();
}


// 전체조회
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // 수정
    @Transactional
    public void updateComment(Long commentId, String Author, String Text) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 id의 게시글을 찾을 수 없음"));

        comment.setAuthor(comment.getAuthor());
        comment.setText(comment.getText());

        // 저장
        commentRepository.save(comment);
    }

    // 삭제
    public boolean deleteComment(long commentId) {
        try {
            if (commentRepository.existsById(commentId)) {
                commentRepository.deleteById(commentId);
                return true; // 게시글 삭제 성공
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
