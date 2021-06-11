package ball.ingram.demo.controller;


import ball.ingram.demo.dto.PaginationDTO;
import ball.ingram.demo.mapper.QuestionMapper;
import ball.ingram.demo.mapper.UserMapper;
import ball.ingram.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size,
                        @RequestParam(value = "search",required = false) String search
    ){

        PaginationDTO paginationDTO = questionService.list(search,page,size);

        model.addAttribute("paginationDTO",paginationDTO);
        model.addAttribute("search",search);

        return "index";
    }
}
