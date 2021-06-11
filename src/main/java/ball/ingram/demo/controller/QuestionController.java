package ball.ingram.demo.controller;

import ball.ingram.demo.dto.CommentDTO;
import ball.ingram.demo.dto.QuestionDTO;
import ball.ingram.demo.enums.CommentTypeEnum;
import ball.ingram.demo.service.CommentService;
import ball.ingram.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @RequestMapping("/question/{id}")
    public String getById(@PathVariable("id") Long id,
                          Model model){
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> questionDTOList = questionService.selectRelated(questionDTO);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOS",commentDTOS);
        model.addAttribute("questionDTOList",questionDTOList);
        return "question";
    }
}
