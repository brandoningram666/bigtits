package ball.ingram.demo.controller;

import ball.ingram.demo.cache.TagCache;
import ball.ingram.demo.dto.QuestionDTO;
import ball.ingram.demo.mapper.QuestionMapper;
import ball.ingram.demo.mapper.UserMapper;
import ball.ingram.demo.model.Question;
import ball.ingram.demo.model.User;

import ball.ingram.demo.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish( Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(name = "title",required = false) String title,
            @RequestParam(name = "description",required = false) String description,
            @RequestParam(name = "tag",required = false) String tag,
            @RequestParam(name = "id",required = false) Long id,
            HttpServletRequest request,
            Model model
    ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);
        model.addAttribute("tags", TagCache.get());

        if(description == null || description ==""){
            model.addAttribute("error","问题补充不为空");
            return "publish";
        }
        if(tag == null || tag ==""){
            model.addAttribute("error","标签不为空");
            return "publish";
        }
        if(title == null || title ==""){
            model.addAttribute("error","标题不为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publish";
        }

        User user =(User) request.getSession().getAttribute("user");
        if(user == null ){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();

        question.setDescription(description);
        question.setCreator(user.getId());
        question.setTag(tag);
        question.setTitle(title);
        question.setId(id);
        questionService.createOrUpdate(question);
        return  "redirect:/";


    }
}
