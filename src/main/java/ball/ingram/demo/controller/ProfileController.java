package ball.ingram.demo.controller;

import ball.ingram.demo.dto.PaginationDTO;
import ball.ingram.demo.mapper.UserMapper;
import ball.ingram.demo.model.User;
import ball.ingram.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          Model model){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null ){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(),page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "profile";

    }
}
