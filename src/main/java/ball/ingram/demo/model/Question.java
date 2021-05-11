package ball.ingram.demo.model;

import lombok.Data;

@Data
public class Question {
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


}
