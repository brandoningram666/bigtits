package ball.ingram.demo.mapper;

import ball.ingram.demo.dto.QuestionQueryDTO;
import ball.ingram.demo.model.Question;
import ball.ingram.demo.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incComment(Question record);

    int incView(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}