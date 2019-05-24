package com.example.demohealth.controller;

import com.example.demohealth.dao.IUserDao;
import com.example.demohealth.model.User;
import com.example.demohealth.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, IUserDao userDao) {
        this.userService = userService;
//        this.userDao = userDao;
    }

    /*
     * login
     * http://localhost:8080/login?username=a&password=123
     * */
//    @GetMapping(path = "/login")
//    public User login(String username, String password) {
//        System.out.println("Controller.login");
//        return userService.login(username, password).get(0);
//    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /*
    * 检测登录信息
    * */
    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        HttpSession sessoin = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");


        if (!password.equals(userService.login(username).get(0).getPassword())) {
            request.setAttribute("msg", "用户名或密码不正确！");
            return "login";
        } else {
            sessoin.setAttribute("username", username);
            sessoin.setAttribute("password", password);
            /*
             *  category 1:child
             *           0:parent
             * */
            if (userService.login(username).get(0).getCategory().equals(1)) {
                return "redirect:/healthConditionChild";  //跳到子女端的首页
            } else {
                return "redirect:/healthConditionParent";  //跳到父母端的首页
            }
        }
    }

//        }else{
//            request.setAttribute("msg", "用户不存在！");
//            return "redirect:/login";
//        }

//        if (check) {
//            sessoin.setAttribute("username", username);
//            sessoin.setAttribute("password", password);
//            /*
//            *  category 1:child
//            *           0:parent
//            * */
//            if (userService.login(username, password).get(0).getCategory().equals(1)){
//                return "redirect:/healthConditionChild";  //跳到子女端的首页
//            }else{
//                return "redirect:/healthConditionParent";  //跳到父母端的首页
//            }

//
//
//        } else {
//            request.setAttribute("msg", "用户名或密码不正确！");
//            return "redirect:/login";
//        }
//    }

    /*
    *子女登录成功 跳转到子女端的首页
    * */
    @RequestMapping(value = "/healthConditionChild", method = RequestMethod.GET)
    public String loginSuccessChild() {
        return "healthConditionChild";
    }

    /*
     *父母登录成功 跳转到父母端的首页
     * */
    @RequestMapping(value = "/healthConditionParent", method = RequestMethod.GET)
    public String loginSuccessParent() {
        return "healthConditionParent";
    }

    /*
     * 注册新用户  此处数据传递存疑！！！！！！
     * */
    @PostMapping(path = "/doRegister")
    public String doRegister(User user , Model model)throws Exception{
        try {
            //设置新注册学生的权限为rookie 初级
            userService.register(user);
            return "redirect:/login";
        }
        catch (Exception e){
            String tip = e.getMessage();
            model.addAttribute("msg", tip);
            return "user/register";
        }
    }

}
