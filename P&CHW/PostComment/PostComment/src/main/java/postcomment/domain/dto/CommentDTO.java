package postcomment.domain.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import postcomment.domain.entity.Post;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentDTO {
    private Long commentId;
    private String author;
    private String text;
    private Post post;
}