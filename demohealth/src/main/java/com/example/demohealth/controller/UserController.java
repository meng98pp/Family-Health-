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
import java.util.Map;

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
     * 注册新用户 （尚无对新用户信息的判定处理（如用户名不能重复））
     * */
    @GetMapping(path="/doRegister")
    public String doRegister(HttpServletRequest request,String username,String password,
                             String telephone,String address,Integer category){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setAddress(address);
            user.setTelephone(telephone);
            user.setCategory(category);

            userService.save(user);
            return "redirect:/login";
    }

    /*
     * 修改密码  新旧密码进行匹配，若密码不一致，则保存
     * */
    @GetMapping(path = "/resetPassword")
    public String reset_password(HttpServletRequest request, String username, String password_old, String password_new) {
        HttpSession sessoin = request.getSession();

        if (!password_new.equals(password_old)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password_new);
            user.setId(userService.findId(username).get(0).getId());

            userService.save(user);

            return "redirect:/login";
        } else {
            request.setAttribute("msg", "新旧密码一致，请重新输入！");
        }
        return "redirect:/login";
    }

    /*
     * 显示用户信息    (Session共享还没有写)
     * */
    @GetMapping(path = "/showInfo")
    public String showInfo(Map<String, Object> map){

        map.put("username", "Hello, Spring Boot!");

        map.put("message2", "Hello, Spring Boot!");

        User user = new User(18, "Bruce");

        map.put("user", user);

        return "userInfo";

    }

}
