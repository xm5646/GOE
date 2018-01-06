package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.project.goe.projectgeodbserver.entity.EarningLog;
import com.project.goe.projectgeodbserver.service.EarningLogService;

@RestController
@RequestMapping("/elog")
public class EarningLogController {
	@Autowired
	private EarningLogService earningLogService;
	
	/**
	 * @Title：save
	 * @Description：接受前端POST请求，添加一条收益日志
	 * @params：earningLog，POST请求封装对象
	 * @return：返回响应状态码，1000：传入的参数为null，添加失败；200:添加成功
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public int save(@ModelAttribute EarningLog earningLog) {
		if(0==earningLog.getUserid() || 0==earningLog.getMoney() || null==earningLog.getTouchtype() || null==earningLog.getStatus()) {
			return 1000;
		}
		earningLog.setCreateTime(new Date());
		this.earningLogService.save(earningLog);
		return 200;
	}
	
 /************************查询相关**************************/
	/**
	 * @Title：findAll
	 * @Description：查询所有的收益日志
	 * @return：List<EarningLog>
	 */
	@RequestMapping("/findAll")
	public List<EarningLog> findAll() {
		return this.earningLogService.findAll();
	}
	
	/**
	 * @Title：findByUserid
	 * @Description：查询指定用户所有的收益日志
	 * @params： userid，用户userid
	 * @return：List<EarningLog>,返回指定用户的所有收益日志列表
	 * @throws
	 */
	@RequestMapping("/findByUserid/{userid}")
	public List<EarningLog> findByUserid(@PathVariable int userid) {
		return this.earningLogService.findByUserid(userid);
	}
	
	//收益日志  就是发的钱
	//查询方法：20%股份 35%工资提现 35%作为报单币 10%手续费
	
	//公司临时插入个人收益 
	
	//公司更改收益表  增加备注信息  描述信息 （手工校对，补发）
}
