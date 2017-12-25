package com.project.goe.projectgeodbserver.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.entity.Transfer;
import com.project.goe.projectgeodbserver.service.EarningService;

/**
 * 收益控制层，提供Restful服务
 * @author xiaoming
 *
 */
@RestController
@RequestMapping("/earning")
public class EarningController {

	@Resource
	private EarningService earningService;
	
	@RequestMapping("/save")
	public String save() {
		return "插入成功";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		earningService.delete(id);
		return "id" + id;
	}
	
	@RequestMapping("/findAll")
    public Iterable<Earning> getAll(){
       return earningService.getAll();
    }
	
	@RequestMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id) {
    		Earning e = earningService.getEarningById(id);
    		//e.setMoney(.getMoney()+100);
    		earningService.save(e);
    		return "user update ok";
    }
	
	
	
}
