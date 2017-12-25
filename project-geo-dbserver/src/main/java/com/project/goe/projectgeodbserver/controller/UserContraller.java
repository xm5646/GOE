package com.project.goe.projectgeodbserver.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.transfEntity.UserTransf;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.viewentity.UserVO;

/**
 * 用户控制层，提供restful服务
 * @author zhangqiankun
 *
 */
@RestController
@RequestMapping("/user")
public class UserContraller {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/save")
	public String save() {
		User u = UserUtil.getTestUser();
		u.setWeightCode(1);
		if (userService.getUserByName(u.getName()) !=null) {
			return "用户名重复";
		}else {
			userService.save(u);
			return "数据插入成功";
		}
	}
	
	@RequestMapping("/testsave/{id}")
	public String saveTest(@PathVariable("id") Integer id) {
		User parentUser = userService.getUserById(id);
		if (parentUser.getDepartmentA() < parentUser.getUserid() || parentUser.getDepartmentA()<parentUser.getUserid() ) {
			User newUserA = UserUtil.getTestUser(parentUser,"A");
			User newUserB = UserUtil.getTestUser(parentUser,"b");
			userService.save(newUserA);
			userService.save(newUserB);
			
			int useridA = userService.getUserByName(newUserA.getName()).getUserid();
			int useridB = userService.getUserByName(newUserB.getName()).getUserid();
			parentUser.setDepartmentA(useridA);
			parentUser.setDepartmentB(useridB);
			userService.save(parentUser);
		}
		return "测试数据插入成功";
	}
	
	
	@RequestMapping("/findAll")
	public Iterable<User> getAll(){
		return userService.getAll();
	}
	
	@RequestMapping("/find/{id}")
	public UserVO getView(@PathVariable("id") Integer id) {
		User u = userService.getUserById(id);
		UserVO uvo = UserTransf.transfToVO(u);
		return uvo;
	}
	
    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id) {
    		User u = userService.getUserById(id);
    		return "user update ok";
    }
    
    public String getUserEarningInfo() {
    		return "";
    }
}
