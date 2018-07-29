package com.project.goe.projectgeodbserver.controller;

import java.util.List;

import com.project.goe.projectgeodbserver.entity.SmsCode;
import com.project.goe.projectgeodbserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.RedisService;
import com.project.goe.projectgeodbserver.service.TestService;

@RestController
@RequestMapping("/tUser/")
public class TestController {
    @Autowired
    private TestService testService;


    @Autowired
    private RedisService redisService;


    @RequestMapping(value = "/setStr")
    public String setStr(String key, String val) {
        try {
            redisService.setStr("li", "xiaoming");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "/testList")
    public String addUserIdToList(String key,Long userId) {
        try {
            redisService.pushObjToList("testList", Math.random()*100+1);
            return  "insert success!";
//            return redisService.getObjFromList("testList").toString();
        } catch (Exception e){
            e.printStackTrace();;
            return "error";
        }
    }

    @RequestMapping(value = "/getList")
    public String getUserIdFromList(String key) {
        try {
            if (redisService.getListSize("testList") > 0) {
                return redisService.getObjFromList("testList").toString();
            } else {
                return  "list is empty!";
            }
        } catch (Exception e){
            return  "error";
        }
    }

    @RequestMapping(value = "/exist")
    public Boolean ExistKey(String key) {
        try {
            return redisService.ExistsKey("lixiaoming");
        } catch (Exception e) {
            return  false;
        }
    }

    @RequestMapping(value = "setObj")
    public String setObj(String key, SmsCode smsCode) {
        try {
            SmsCode s1 = new SmsCode();
            s1.setAccount("lixiaoming");
            s1.setCode("1024");
            s1.setOperation("init_user_info");
            redisService.setObj("obj", s1);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "freeTime")
    public String getFreeTime(String key) {
        Long freetime = redisService.getFreeTime(key);
        return  "剩余时间:" + String.valueOf(freetime);
    }

    @RequestMapping(value = "getObj")
    public Object getObj(String key) {
        SmsCode result = (SmsCode) redisService.getObj("key");
        return result;
    }

    @RequestMapping(value = "delObj")
    public Object delObj(String key) {
        try {
            redisService.delObj("obj");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    //新增用户
    @PostMapping("/save")
    public User saveUser(User user) {
        return this.testService.saveUser(user);
    }

    //分页查询所有用户数据
    @GetMapping("/findAll")
    public Page<User> findAll(
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size,
            @RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
        try {
            Sort sort = null;

            if (order.equals("asc"))
                sort = new Sort(Direction.ASC, keyword);
            else
                sort = new Sort(Direction.DESC, keyword);

            //Spring MVC PageRequest实现Pageable接口
            /*
			 * pageNum：起始页
			 * size：每页多少条数据
			 * sort：排序规则，这里基于createTime降序排序
			 */
            Pageable pageable = new PageRequest(pageNum, size, sort);

            return this.testService.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //分页查询数据：基于用户名和用户状态，按创建时间降序查询
    @GetMapping("/findUserByAccountAndUserStatus")
    public Page<User> findUserByAccountAndUserStatus(
            @RequestParam(value = "account", required = true) String account,
            @RequestParam(value = "userStatus", required = true) boolean userStatus,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size,
            @RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
        try {
            Sort sort = null;

            if (order.equals("asc"))
                sort = new Sort(Direction.ASC, keyword);
            else
                sort = new Sort(Direction.DESC, keyword);

            Pageable pageable = new PageRequest(pageNum, size, sort);

            User user = new User();
            user.setAccount(account);
            user.setUserStatus(userStatus);
            return this.testService.findUserByAccountAndCreateTime(user, pageable);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //基于用户名和用户状态查询
    @GetMapping("/queryUserByAccountAndUserStatus")
    public List<User> queryUserByAccountAndUserStatus(User user) {
        return this.testService.queryUserByAccountAndUserStatus(user);
    }

}
