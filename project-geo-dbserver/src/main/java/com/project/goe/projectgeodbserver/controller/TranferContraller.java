package com.project.goe.projectgeodbserver.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Transfer;
import com.project.goe.projectgeodbserver.service.TransferService;

@RestController
@RequestMapping("/tranfer")
public class TranferContraller {
	@Resource
	private TransferService transferService;
	
	@RequestMapping("/save")
	public String save() {
		Transfer t = new Transfer();
		t.setUserid(123);
		t.setCreatetime(new Date());
		t.setMoney(58);
		transferService.save(t);
		return "user save ok";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		transferService.delete(id);
		return "id:"+id;
	}
	
    @RequestMapping("/findAll")
    public Iterable<Transfer> getAll(){
       return transferService.getAll();
    }
    
    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id) {
    		Transfer t = transferService.getTransferById(id);
    		t.setMoney(t.getMoney()+100);
    		transferService.save(t);
    		return "user update ok";
    }
    
  //方法：公司打钱给个人  更新USER表
    
  //方法：个人奖金转换为报单币  更新USER表的属性
    
  //方法：用户间报单币互转  更新USER表数据 两个人数据都要更新
  //方法：消费报单币 -推荐 新增USER表数据
    
  //方法：消费报单币-重销  新增一条重销记录，更新USER表考核状态
}
