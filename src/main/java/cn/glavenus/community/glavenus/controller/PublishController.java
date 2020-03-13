package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.PageinationDTO;
import cn.glavenus.community.glavenus.dto.QuestionDTO;
import cn.glavenus.community.glavenus.model.Question;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.IQuestionService;
import cn.glavenus.community.glavenus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Creaked by EyreValor on 2020/2/28
 */
@Controller
public class PublishController {

    @Autowired
    private IUserService userServiceimpl;

    @Autowired
    private IQuestionService questionServiceimpl;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    /**
     * 编辑问题功能
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model) {
        QuestionDTO questionDTO  = questionServiceimpl.getQuestionById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",questionDTO.getId());
        return "publish";
    }

    /**
     * 创建问题
     *
     * @param title
     * @param description
     * @param tag
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false)Integer id,
            HttpServletRequest request,
            Model model
    ) {
        User user = (User) request.getSession().getAttribute("user");

        //TODO 处理异常后期移动到前端控制
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            setModel(model, title, description, tag);
            return "publish";
        }

        if (title == null || "".equals(title)) {
            model.addAttribute("noTitle", "标题不能为空");
            setModel(model, title, description, tag);
            return "publish";
        }

        if (description == null || "".equals(description)) {
            model.addAttribute("noDescription", "问题补充不能为空");
            setModel(model, title, description, tag);
            return "publish";
        }

        if (tag == null || "".equals(tag)) {
            model.addAttribute("noTag", "标签不能为空");
            setModel(model, title, description, tag);
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(id);
        //创建提问
        questionServiceimpl.createQuestion(question,user);
        return "redirect:/";
    }

    //回显问题页面内容
    private void setModel(Model model, String title, String description, String tag) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
    }
}
