package com.dounine.corgi.sso.consumer.ctrl;

import com.alibaba.fastjson.JSON;
import com.dounine.corgi.commons.ResponseText;
import com.dounine.corgi.exception.SerException;
import com.dounine.corgi.jsonp.Callback;
import com.dounine.corgi.response.ResponseContext;
import com.dounine.corgi.sso.entity.user.User;
import com.dounine.corgi.sso.service.user.IUserSer;
import com.dounine.corgi.sso.session.UserSession;
import com.dounine.corgi.validation.Add;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanghuanlai on 16/8/17.
 */
@RestController
public class UserCtrl {

    @Autowired
    private IUserSer userSer;

    @RequestMapping(value = "all",method = RequestMethod.GET)
    public List<User> all(){
        List<User> users = null;
        try {

            users = userSer.findAll();
        }catch (SerException se){
            System.out.println(se.getMessage());
            //se.printStackTrace();
        }
        return users;
    }

    @RequestMapping(value = "add",method = RequestMethod.GET)
    public void add() throws SerException {
        User user = new User();
        user.setAccessTime(LocalDateTime.now());
        user.setPassword("666");
        user.setUsername("lgq");
        userSer.save(user);
    }

    @RequestMapping(value = "update",method = RequestMethod.GET)
    public void update() throws SerException {
        User user = new User();
        Object u = userSer.findById("57e0ee775c142304d18e7d1a");
        user.setUsername("hello world!");
        userSer.update(user);
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseText login(@Validated(Add.class) User user, BindingResult result, @Callback String callback) throws Throwable {
        boolean callbackFun = StringUtils.isNotBlank(callback);
        ResponseText rt = null;
        String token = userSer.login(user);
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotBlank(token)){
            if(callbackFun){
                sb.append(callback);
                sb.append(String.format("({token:\"%s\"})",token));
                ResponseContext.writeData(sb);
            }else{
                rt = new ResponseText();
                rt.setData(String.format("{token:\"%s\"}",token));
                Cookie tokenCookie = new Cookie("token",token);
                ResponseContext.get().addCookie(tokenCookie);
            }
        }else{
            if(callbackFun){
                sb.append(callback);
                sb.append("({msg:\"LOGIN-0002\"})");
                ResponseContext.writeData(sb);
            }else{
                rt = new ResponseText();
                rt.setErrno(2);
                rt.setMsg("LOGIN-0002");
            }
        }
        return rt;
    }

    @RequestMapping(value = "{token}/verify",method = RequestMethod.GET)
    public ResponseText verify(@PathVariable String token){
        return new ResponseText(String.format("{verify:%s}", UserSession.verify(token)));
    }

    @RequestMapping(value = "verify",method = RequestMethod.GET)
    public ResponseText verifyC(@CookieValue String token){
        return verify(token);
    }

}
