package ball.ingram.demo.dto;

import ball.ingram.demo.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentor;
    private Long likeCount;
    private Integer commentCount;
    private Long gmtCreate;
    private Long gmtModified;
    private String content;
    private User user;
}
