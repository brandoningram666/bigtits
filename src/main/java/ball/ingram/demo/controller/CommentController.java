package ball.ingram.demo.controller;

import ball.ingram.demo.dto.CommentCreateDTO;
import ball.ingram.demo.dto.CommentDTO;
import ball.ingram.demo.dto.ResultDTO;
import ball.ingram.demo.enums.CommentTypeEnum;
import ball.ingram.demo.exception.CustomizeErrorCode;
import ball.ingram.demo.model.Comment;
import ball.ingram.demo.model.User;
import ball.ingram.demo.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    //评论
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        if(user == null ){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if(StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentor(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return  ResultDTO.okOf();

    }
    //返回评论
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable("id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
