package com.project.goe.projectgeodbserver.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.repository.UserRepositoy;
import com.project.goe.projectgeodbserver.util.DateFormatUtil;

@Service
public class UserService {
	@Autowired
	private UserRepositoy userRepositoy;
	
	//新增用户
	@Transactional
	public User save(User user) {
		return this.userRepositoy.save(user);
	}
	
	//批量更新或者新加用户
	@Transactional
	public List<User> saveAll(List<User> userupdate) {
		if (userupdate!=null && userupdate.size()>0) {
			for (User user : userupdate) {
				this.userRepositoy.save(user);
			}
		}
		return userupdate;
	}
	
	//删除用户
	@Transactional
	public void delete(User user) {
		this.userRepositoy.delete(user);
	}
	
	//删除所有用户数据
	public void deleteAllUser() {
		this.userRepositoy.deleteAll();
	}
	
/****************查询相关*******************************/
	//基于account，查询用户信息
	public User findByAccount(String account) {
		return this.userRepositoy.findByAccount(account);
	}
	
	@Transactional
	public void delete(Long id) {
		userRepositoy.delete(id);
	}
	
	@Transactional
	public Iterable<User> getAll(){
		return userRepositoy.findAll();
	}
	
	@Transactional
	public User getUserById(Long id){
		return userRepositoy.findOne(id);
	}
	//基于userId，查询用户信息
	public User findByUserId(long userId) {
		return this.userRepositoy.findByUserId(userId);
	}
	//分页查询
	public Page<User> findAllUserBySort(Pageable pageable) {
		return this.userRepositoy.findAll(pageable);
	}
	
	//查询当月新增用户
	public List<User> findByCreateTimeBetweenDate() {
		List<Date> dateList = DateFormatUtil.getStartDateAndEndDateOfNowMonth();
		return this.userRepositoy.findByCreateTimeBetween(dateList.get(0), dateList.get(1));
	}
	
	//查询上一周新增用户
	public List<User> findByCreateTimeBetweenWeek() {
		List<Date> dateList = DateFormatUtil.getStartDayAndEndDayOfLastWeek();
		return this.userRepositoy.findByCreateTimeBetween(dateList.get(0), dateList.get(1));
	}
}
