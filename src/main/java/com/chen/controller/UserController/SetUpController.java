package com.chen.controller.UserController;

import com.chen.MyUtils.RandomCode;
import com.chen.MyUtils.SendEmail;
import com.chen.MyUtils.TimeUtil;
import com.chen.Service.adminService.UserService;
import com.chen.Service.uploadService.UploadService;
import com.chen.pojo.RegisterUser;
import com.chen.pojo.UploadResp;
import com.chen.pojo.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Objects;

@Controller
public class SetUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private TimeUtil timeUtil;

    private HashMap<String, String> emailMap; // 放要更新的邮箱

    private HashMap<String, String> codeMap;

    @PostConstruct
    public void init(){
        codeMap = new HashMap<>();
    }

    @GetMapping("/setUp")
    public String setUp(Model model, HttpSession session){
        model.addAttribute("message", "all"); //前端显示设hi主页面
        User loginUser = (User) session.getAttribute("loginUser");
        return "user/setUp";
    }

    @GetMapping("/updatePassword")
    public String updatePasswordPage(Model model){
        model.addAttribute("message", "password"); //前端显示修改密码的元素
        return "user/setUp";
    }

    @GetMapping("/checkEmail")
    public String checkEmail(Model model){
        model.addAttribute("message", "checkEmail"); //前端显示验证旧邮箱的元素
        return "user/setUp";
    }

    @GetMapping("/updateEmail")
    public String updateEmail(Model model){
        model.addAttribute("message", "updateEmail"); //前端显示修改邮箱的元素
        return "user/setUp";
    }

    @GetMapping("submitSetUp")
    public String submitSetUp(User user, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        user.setId(loginUser.getId());//放id进去，或者在表单隐藏域传入
        userService.updateUserLimit(user);
        loginUser = userService.getUserById(loginUser.getId());
        session.setAttribute("loginUser", loginUser);
        return "redirect:setUp";
    }

    @PostMapping("/uploadAvatar")
    @ResponseBody
    public UploadResp upload(MultipartFile file, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        UploadResp upload = uploadService.upload(file, true);
        userService.updateOnlyOne(loginUser.getId(), upload.getFileName(), "avatar");
        loginUser = userService.getUserById(loginUser.getId());
        session.setAttribute("loginUser", loginUser);
        return upload;
    }

    @GetMapping("/updateSendCode") //不要改成sendCode, 跟register中的冲突
    @ResponseBody
    public String updateSendCode(String newEmail, HttpSession session){

        User loginUser = (User) session.getAttribute("loginUser");

        if (Objects.nonNull(session.getAttribute("codeTime"))){
            String codeTime = String.valueOf(session.getAttribute("codeTime"));
            if (StringUtils.hasText(codeTime)){
                long time = Long.parseLong(codeTime);
                long remainingTime = timeUtil.getTime(time);
                if (remainingTime < 60){
                    return "已发送，请" + (60 - remainingTime) + "秒后重试！";
                }
            }
        }

        //判断是单纯发code还是要改绑邮箱
        String code;
        if (StringUtils.hasText(newEmail)) {
            if (!newEmail.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$")){
                return "邮箱格式错误！";
            }
            emailMap.put(session.getId(), newEmail);

            code = RandomCode.getRandomCode();
            codeMap.put(session.getId(), code);
            sendEmail.send(loginUser.getEmail(), "验证码为：" + code + "（请注意区分大小写！）");
            //用新邮箱
        }else {
            code = RandomCode.getRandomCode();
            codeMap.put(session.getId(), code);
            sendEmail.send(loginUser.getEmail(), "验证码为：" + code + "（请注意区分大小写！）");
        }

        //timeUtil.start();
        session.setAttribute("codeTime", timeUtil.setTime());
        System.out.println(codeMap);
        return "发送成功！";
    }

    @GetMapping("/submitPasswordUpdate")
    @ResponseBody
    public String submitPasswordUpdate(RegisterUser registerUser, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        //直接用这个虚拟的实体类接收，懒
        //判断code 跟 两次密码相同,其实应该在Service层做的，懒了
        if (StringUtils.hasText(registerUser.getCode())){
            if (!registerUser.getCode().equals(codeMap.get(session.getId()))){
                return "验证码错误！";
            }
        }else {
            return "验证码不能为空！";
        }

        if (StringUtils.hasText(registerUser.getPassword()) && StringUtils.hasText(registerUser.getAgainPassword())){
            if (!registerUser.getPassword().equals(registerUser.getAgainPassword())){
                return "两次密码不一致！";
            }
        } else {
            return "密码不能为空！";
        }
        // dao层修改没写
        userService.updateOnlyOne(loginUser.getId(), registerUser.getPassword(), "password");
        //验证成功后将验证码清除
        codeMap.put(session.getId(), "");
        return "修改成功！";
    }

    @GetMapping("/checkOldEmail")
    @ResponseBody
    public String checkEmail(String code, HttpSession session){
        if (StringUtils.hasText(code)){
            if (!code.equals(codeMap.get(session.getId()))){
                return "验证码错误！";
            }
        }else {
            return "验证码不能为空！";
        }
        //验证成功后将验证码清除
        codeMap.put(session.getId(), "");
        return "success";
    }

    @GetMapping("/submitUpdateEmail")
    @ResponseBody
    public String submitUpdateEmail(String newEmail, String code, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        if (!StringUtils.hasText(emailMap.get(session.getId())) || !StringUtils.hasText(codeMap.get(session.getId()))){
            return "请先发送验证码！";
        }
        if (!emailMap.get(session.getId()).equals(newEmail)){
            return "前后邮箱不一致!";
        }
        if (!codeMap.get(session.getId()).equals(code)){
            return "验证码错误！";
        }
        //验证成功后将验证码清除
        codeMap.put(session.getId(), "");
        userService.updateOnlyOne(loginUser.getId(), newEmail, "email");
        return "更改成功！";
    }


}
