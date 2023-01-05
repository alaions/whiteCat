package com.chen.controller.UserController;
import com.chen.Service.adminService.TagService;
import com.chen.Service.adminService.TopicService;
import com.chen.Service.adminService.UserService;
import com.chen.Service.uploadService.UploadService;
import com.chen.pojo.Topic;
import com.chen.pojo.UploadResp;
import com.chen.pojo.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

@Controller
public class WriteController {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private TagService tagService;


    private HashMap<String, String> pictureMap; // 存每个用户上传的封面

    @PostConstruct
    public void init(){
        pictureMap = new HashMap<>();
    }

    @GetMapping("/write.html")
    public String write(Model model, HttpSession session){
        model.addAttribute("tagList", tagService.getTagList());
        pictureMap.put(session.getId(), "");
        return "user/write";
    }

    @GetMapping("/submitTopic")
    @ResponseBody
    public String submitTopic(Topic topic, HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        topic.setTopicTime(new Date());
        topic.setTopicUserId(loginUser.getId());
        if (StringUtils.isEmpty(pictureMap.get(session.getId()))){
            return "封面不能为空！";
        }
        topic.setTopicPicture(pictureMap.get(session.getId()));
        pictureMap.remove(session.getId());
        topicService.insertTopic(topic);

        userService.topicCountPlus(topic.getTopicUserId()); //文章数量+1
        session.setAttribute("loginUser", userService.getUserById(loginUser.getId()));

        return "发布成功!";
    }


    @PostMapping("/upload")
    @ResponseBody
    public UploadResp upload(MultipartFile file, HttpSession session){
        UploadResp upload = uploadService.upload(file, false);
        // 给封面赋值，防止为空
        //this.topicPicture = upload.getFileName(); //用map存啊
        pictureMap.put(session.getId(), upload.getFileName());
        return upload;
    }

}
