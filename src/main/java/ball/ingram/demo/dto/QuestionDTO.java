package ball.ingram.demo.dto;

import ball.ingram.demo.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private int creator;
    private String tag;
    private User user;
}
