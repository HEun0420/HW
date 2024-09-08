package postcomment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import postcomment.common.ResponseMsg;
import postcomment.domain.dto.CommentDTO;
import postcomment.domain.entity.Comment;
import postcomment.global.CommentNotFoundException;
import postcomment.global.PostNotFoundException;
import postcomment.service.CommentService;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Spring Boot Swagger 연동 API (Board)")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class CommentController {
    private final CommentService commentService;

    // 작성
    @Operation(summary = "댓글 작성", description = "게시판에 업로드할 새로운 댓글 작성")
    @PostMapping("/comments")
    public ResponseEntity<ResponseMsg> createNewComment(@RequestBody CommentDTO newComment) {

        commentService.registComment(newComment);

        String successMsg = "댓글 등록에 성공하였습니다.";

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", successMsg);

        return ResponseEntity
                .ok()
                .body(new ResponseMsg(201, "댓글 추가 성공", responseMap));

    }


// 전체 조회
    @Operation(summary = "댓글 전체 조회", description = "사이트의 댓글 전체 조회")
    @GetMapping("/comments")
    public ResponseEntity<ResponseMsg> findAllComments() {

        List<Comment> comment = commentService.getAllComments();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("comments", comment);


        return ResponseEntity.ok().body(new ResponseMsg(200, "조회성공", responseMap));
    }


    // 단일 조회
    @Operation(
            summary = "댓글 번호로 특정 댓글 조회",
            description = "댓글 번호를 통해 특정 댓글을 조회한다.",
            parameters = {
                    @Parameter(
                            name = "commentId",
                            description = "사용자 화면에서 넘어오는 comment의 pk"
                    )
            })
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ResponseMsg> findCommentByCommentId(@PathVariable long commentId) throws CommentNotFoundException, PostNotFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType(
                        "application",
                        "json",
                        Charset.forName("UTF-8")
                )
        );


      /* 밑의 식에서 오류가 남 -> findByComment를 대신할 JPA의 명칭을 모르겠다.
      * 팀플로 했던 crud를 접목시켰는데, 이걸로 post과 comment가 연동되는건지 모르겠다.
      * gtp가 하라는 대로 이름을 getCommentByCommentId로 바꿨는데 뭔가 되기는 했다.
      * 근데 이게 post랑 comment와 연동이 되는지 모르겠다.
      */


        CommentDTO foundComment = commentService.getCommentByCommentId(commentId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("comment", foundComment);


        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMsg(200, "단일조회성공", responseMap));

    }

    // 수정
    @Operation(summary = "댓글 수정", description = "특정 댓글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "203", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력된 파라미터")
    })
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable long commentId, @RequestBody CommentDTO modifiedComment) throws CommentNotFoundException {
        commentService.updateComment(commentId, modifiedComment.getAuthor(), modifiedComment.getText());

        Map<String, Object> responseMap = new HashMap<>();
        String msg = "댓글 수정에 성공하였습니다.";
        responseMap.put("result", msg);

        return ResponseEntity
                .ok()
                .body(new ResponseMsg(203, "댓글 수정 성공", responseMap));
    }

    // 삭제
    @Operation(summary = "댓글 삭제", description = "특정 댓글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력된 파라미터")
    })
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable long commentId) throws CommentNotFoundException {
        Map<String, Object> responseMap = new HashMap<>();

        boolean isDeleted = commentService.deleteComment(commentId);
        if (isDeleted) {
            String msg = "댓글 삭제에 성공하였습니다.";
            responseMap.put("result", msg);
        } else {
            throw new CommentNotFoundException("댓글 삭제에 실패하였습니다.");
        }

        return ResponseEntity
                .ok()
                .body(new ResponseMsg(204, "댓글 삭제 성공", responseMap));
    }
}
