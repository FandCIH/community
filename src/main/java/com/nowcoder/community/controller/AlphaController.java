package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //获取请求数据
        //获取请求方式
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath()); //获取请求路径
        Enumeration<String> enumeration = request.getHeaderNames();//获取请求行
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement(); //key
            String value = request.getHeader(name); //value，通过request得到
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));

        //respone，用来给浏览器返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try(PrintWriter writer = response.getWriter();)  {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // 1. GET请求--浏览器向服务器获取数据
    //查询所有学生--分页显示(current=1当前第一页；一页最多多少数据(limit=20)  /students?current=1%limit=2

    @RequestMapping(path = "/student", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current, //required = false 意思是也可以不传
            @RequestParam(name = "limit", required = false, defaultValue = "20") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }
    //2. 返回一个学生 /student/123
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

    // POST请求，浏览器向服务器提交数据
    //要想提交数据，浏览器需要带有表单的网页
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //向浏览器响应HTML--由模板引擎thymeleaf创建
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav= new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",30);
        mav.setViewName("/demo/view");
        return mav;
    }

    //和上面一样，但是更简单一些，建议用这种方式
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "北京大学");
        model.addAttribute("age",80);
        return "/demo/view";
    }

    //响应JSON数据(异步请求)
    //Java对象 -> JSON字符串 -> JS对象
    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",20);
        emp.put("salary", 8000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","李三");
        emp.put("age",20);
        emp.put("salary", 9000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","王五");
        emp.put("age",20);
        emp.put("salary", 10000);
        list.add(emp);

        return list;
    }
}
