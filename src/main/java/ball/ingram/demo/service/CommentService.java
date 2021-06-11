package ball.ingram.demo.service;

import ball.ingram.demo.dto.CommentDTO;
import ball.ingram.demo.enums.CommentTypeEnum;
import ball.ingram.demo.enums.NotificationStatusEnum;
import ball.ingram.demo.enums.NotificationTypeEnum;
import ball.ingram.demo.exception.CustomizeErrorCode;
import ball.ingram.demo.exception.CustomizeException;
import ball.ingram.demo.mapper.*;
import ball.ingram.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void insert(Comment comment, User commentator) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        //回复评论
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null ){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FONUD);
            }

            commentMapper.insert(comment);
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if(question == null ){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            //创建通知
            createNotification(comment, dbComment.getCommentor(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        }
        //回复问题
        if(comment.getType() == CommentTypeEnum.QUESTION.getType()){
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null ){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
             comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);

            createNotification(comment,question.getCreator(),commentator.getName(),question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }

    }
    //创建通知
    private void createNotification(Comment comment, Long receiver, String notifierName, String ouetTitle, NotificationTypeEnum notificationTypeEnum, Long outerId) {
        if(receiver == comment.getCommentor()){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setNotifier(comment.getCommentor());
        notification.setReceiver(receiver);
        notification.setOuterId(outerId);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(ouetTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        Set<Long> commentotrs = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentotrs);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
