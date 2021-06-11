package ball.ingram.demo.dto;

import ball.ingram.demo.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private String outTile;
    private User notifier;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer type;
    private Long outerId;

}
