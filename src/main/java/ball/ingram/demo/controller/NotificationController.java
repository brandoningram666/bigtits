package ball.ingram.demo.controller;

import ball.ingram.demo.dto.NotificationDTO;
import ball.ingram.demo.dto.PaginationDTO;
import ball.ingram.demo.enums.NotificationTypeEnum;
import ball.ingram.demo.model.User;
import ball.ingram.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id,
                          HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null ){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
        || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else{
            return "redirect:/";

        }



    }
}
