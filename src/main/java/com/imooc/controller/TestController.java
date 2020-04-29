package com.imooc.controller;

import com.imooc.object.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Description: springMVC数据绑定
 * @Author: chenyawei
 * @Create: 12:48 2020-04-29
 */
@Controller
public class TestController {

    //todo     http://localhost:8080/baseType.do?age=10
    //todo 500 http://localhost:8080/baseType.do
    //todo 400 http://localhost:8080/baseType.do?age=abc
    /**
     * @Description: 基本类型int类型参数，不能为空
     * @Param:
     * @return:
     * @Author: chenyawei
     * @Create: 12:47 2020-04-29
     */
    @RequestMapping(value = "baseType.do")
    @ResponseBody
    public String baseType(int age){
        return "age:"+age;
    }

    /**
     * @Description: 包装类型参数
     * @Param:
     * @return:
     * @Author: chenyawei
     * @Create: 12:47 2020-04-29
     */
    //todo     http://localhost:8080/baseType2.do?age=10
    //todo     http://localhost:8080/baseType2.do
    //todo 400 http://localhost:8080/baseType2.do?age=abc
    @RequestMapping(value = "baseType2.do")
    @ResponseBody
    public String baseType2(Integer age){
        return "age:"+age;
    }

    /**
     * @Description: 数组类型参数
     * @Param:
     * @return:
     * @Author: chenyawei
     * @Create: 12:48 2020-04-29
     */
    //todo http://localhost:8080/array.do?name=Tom&name=Lucy&name=Jim
    @RequestMapping(value = "array.do")
    @ResponseBody
    public String array(String[] name){
        StringBuilder sbf = new StringBuilder();
        for(String item : name){
            sbf.append(item).append(" ");
        }
        return sbf.toString();
    }

    //todo http://localhost:8080/object.do?name=Tom&age=10
    //TODO http://localhost:8080/object.do?name=Tom&age=10&contactInfo.phone=10086
    @RequestMapping(value = "object.do")
    @ResponseBody
    public String object(User user,Admin admin){
        return user.toString()+" "+admin.toString();
    }

    //TODO http://localhost:8080/object.do?user.name=Tom&admin.name=Lucy&age=10
    //@InitBinder注解的作用为调用Controller的接口前，先调用该方法.有了这个注解的辅助方法，就可以使用user.name,admin.name这样的传递方式类
    @InitBinder("user")
    public void initUser(WebDataBinder binder){
        binder.setFieldDefaultPrefix("user.");
    }
    @InitBinder("admin")
    public void initAdmin(WebDataBinder binder){
        binder.setFieldDefaultPrefix("admin.");
    }


    //tomcat版本高的URL不能使用[],需要把"["转为%5B 和 把"]"为转%5D  http://localhost:8080/list.do?users%5B0%5D.name=Tom&users%5B1%5D.name=Lucy
    //TODO http://localhost:8080/list.do?users[0].name=Tom&users[1].name=Lucy
    //TODO http://localhost:8080/list.do?users[0].name=Tom&users[1].name=Lucy&users[20].name=Jim，这里有一个坑的list下标要是连续的
    @RequestMapping(value = "list.do")
    @ResponseBody
    public String list(UserListForm userListForm){
        return "listSize:"+userListForm.getUsers().size() + "  " + userListForm.toString();
    }

    //使用set集合时需要初始化
    //TODO http://localhost:8080/set.do?users[0].name=Tom&users[20].name=Lucy
    @RequestMapping(value = "set.do")
    @ResponseBody
    public String set(UserSetForm userSetForm){
        return userSetForm.toString();
    }

    //TODO http://localhost:8080/map.do?users['X'].name=Tom&users['X'].age=10&users['Y'].name=Lucy
    // http://localhost:8080/map.do?users%5B'X'%5D.name=Tom&users%5B'X'%5D.age=10&users%5B'Y'%5D.name=Lucy
    @RequestMapping(value = "map.do")
    @ResponseBody
    public String map(UserMapForm userMapForm){
        return userMapForm.toString();
    }


//    {
//        "name": "Jim",
//            "age": 16,
//            "contactInfo": {
//                "address": "beijing",
//                "phone": "10010"
//              }
//    }
    //application/json  用post请求
    @RequestMapping(value = "json.do")
    @ResponseBody
    public String json(@RequestBody User user){
        return user.toString();
    }


//    <?xml version="1.0" encoding="UTF-8" ?>
//    <admin>
//      <name>Jim</name>
//      <age>16</age>
//    </admin>
    //application/xml
    @RequestMapping(value = "xml.do")
    @ResponseBody
    public String xml(@RequestBody Admin admin){
        return admin.toString();
    }

    @RequestMapping(value = "date1.do")
    @ResponseBody
    public String date1(Date date1){
        return date1.toString();
    }

//    @InitBinder("date1")
//    public void initDate1(WebDataBinder binder){
//        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
//    }

    @RequestMapping(value = "date2.do")
    @ResponseBody
     public String date2(Date date2){
        return date2.toString();
    }



    @RequestMapping(value = "/book",method = RequestMethod.GET)
    @ResponseBody
    public String book(HttpServletRequest request){
        String contentType = request.getContentType();
        if(contentType == null){
            return "book.default";
        }else if(contentType.equals("txt")){
            return "book.txt";
        }else if(contentType.equals("html")){
            return "book.html";
        }
        return "book.default";
    }

    @RequestMapping(value = "/subject/{subjectId}",method = RequestMethod.GET)
    @ResponseBody
    public String subjectGet(@PathVariable("subjectId") String subjectId){
        return "this is a get method,subjectId:"+subjectId;
    }

    @RequestMapping(value = "/subject/{subjectId}",method = RequestMethod.POST)
    @ResponseBody
    public String subjectPost(@PathVariable("subjectId") String subjectId){
        return "this is a post method,subjectId:"+subjectId;
    }

    @RequestMapping(value = "/subject/{subjectId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String subjectDelete(@PathVariable("subjectId") String subjectId){
        return "this is a delete method,subjectId:"+subjectId;
    }

    @RequestMapping(value = "/subject/{subjectId}",method = RequestMethod.PUT)
    @ResponseBody
    public String subjectPut(@PathVariable("subjectId") String subjectId){
        return "this is a put method,subjectId:"+subjectId;
    }













    @RequestMapping(value = "converter.do")
    @ResponseBody
    public String converter(Boolean bool){
        return bool.toString();
    }


}
