package com.example.demohealth.controller;

import com.example.demohealth.model.Connect;
import com.example.demohealth.model.User;
import com.example.demohealth.service.impl.ConnectService;
import com.example.demohealth.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ConnectService connectService;

    @Autowired
    public UserController(UserService userService, ConnectService connectService) {
        this.userService = userService;
        this.connectService = connectService;
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
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

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
            User user=userService.findId(username).get(0);
            sessoin.setAttribute("usernameTrue", username);  //真实登录进来的Username
            sessoin.setAttribute("password", password);
            sessoin.setAttribute("telephoneTrue", user.getTelephone());
            sessoin.setAttribute("addressTrue",user.getAddress());
            sessoin.setAttribute("categorytrue",user.getCategory());
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
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }
    @PostMapping(path="/doRegister")
    public String doRegister(HttpServletRequest request){
        HttpSession sessoin = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String category = request.getParameter("category");
        int cate;
        if (category.equals("parent_register")){
            cate=0;
        }else{
            cate=1;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAddress(address);
        user.setTelephone(telephone);
        user.setCategory(cate);

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
     * 显示用户信息(用户名 电话 住址 已有的联系)
     * */
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String information() {
        return "showInfo";
    }
    @GetMapping(path = "/showInfo")
    public String showInfo(HttpServletRequest request){

//        String username=request.getParameter("usernameTrue");
//        User user=userService.findId(username).get(0);
//
//        //显示用户的用户名、电话、地址信息
//        HttpSession sessoin = request.getSession();
//        sessoin.setAttribute("telephoneTrue", user.getTelephone());
//        sessoin.setAttribute("addressTrue",user.getAddress());
        HttpSession sessoin = request.getSession();
        sessoin.setAttribute("test",123456);
        String username = request.getParameter("usernameTrue");
        int cate=Integer.parseInt(request.getParameter("categoryTrue"));
        if (cate==1){  //子女
            if (connectService.findByChild(username).get(0)!=null) {
                String userConnect = connectService.findByChild(username).get(0).getUsernameParent();
                sessoin.setAttribute("connectTrue",userConnect);
            }else{
                sessoin.setAttribute("connectTrue",null);
            }
        }else{
            if (connectService.findByParent(username).get(0)!=null) {
                String userConnect = connectService.findByParent(username).get(0).getUsernameChild();
                sessoin.setAttribute("connectTrue",userConnect);
            }else{
                sessoin.setAttribute("connectTrue",null);
            }
        }

        return "showInfo";
    }


    /*
     * 显示用户信息(用户名 电话 住址 已有的联系)
     * */
    @GetMapping(path = "/addConnect")
    public String addConnect(HttpServletRequest request){

        HttpSession sessoin = request.getSession();
        String usernameConnect = request.getParameter("addConnectName");  //新添加关联的用户名
        String username=request.getParameter("usernameTrue");
        int cate=Integer.parseInt(request.getParameter("categoryTrue"));
        if (cate==1){  //子女
            Connect connect=new Connect();
            connect.setUsernameChild(username);
            connect.setUsernameParent(usernameConnect);
            connectService.save(connect);

            sessoin.setAttribute("conncetTrue", usernameConnect);
        }else{
            Connect connect=new Connect();
            connect.setUsernameChild(usernameConnect);
            connect.setUsernameParent(username);
            connectService.save(connect);

            sessoin.setAttribute("conncetTrue", usernameConnect);
        }

        return "addConnect";
    }


}
