package ball.ingram.demo.mapper;

import ball.ingram.demo.model.Comment;
import ball.ingram.demo.model.CommentExample;
import ball.ingram.demo.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}