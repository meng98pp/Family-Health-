package com.example.demohealth.controller;

import com.example.demohealth.dao.IUserDao;
import com.example.demohealth.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class Controller {
    private final IUserDao userDao;
    private final UserService userService;

    @Autowired
    public Controller(UserService userService, IUserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
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

    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        HttpSession sessoin = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");
        boolean check;
        if (userService.login(username, password).get(0) != null) {
            check = true;
        }else {
            check = false;
        }
//        }else{
//            request.setAttribute("msg", "用户不存在！");
//            return "redirect:/login";
//        }

        if (check) {
            sessoin.setAttribute("username", username);
            sessoin.setAttribute("password", password);
            return "redirect:/success";
        } else {
            request.setAttribute("msg", "密码不正确！");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String login_success() {
        return "success";
    }


}
