package com.project.goe.projectgeodbserver.service;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.repository.UserRepositoy;
import com.project.goe.projectgeodbserver.util.DateFormatUtil;
import com.project.goe.projectgeodbserver.viewentity.UserTypeQueryRequest;

@Service
public class UserService {
	@Autowired
	private UserRepositoy userRepositoy;

	// 新增用户
	@Transactional
	public User save(User user) {
		return this.userRepositoy.save(user);
	}

	// 批量更新或者新加用户
	@Transactional
	public List<User> saveAll(List<User> userupdate) {
		if (userupdate != null && userupdate.size() > 0) {
			for (User user : userupdate) {
				this.userRepositoy.save(user);
			}
		}
		return userupdate;
	}

	// 删除用户
	@Transactional
	public void delete(User user) {
		this.userRepositoy.delete(user);
	}

	// 删除所有用户数据
	public void deleteAllUser() {
		this.userRepositoy.deleteAll();
	}

	/**************** 查询相关 *******************************/
	// 基于account，查询用户信息
	public User findByAccount(String account) {
		return this.userRepositoy.findByAccount(account);
	}
	
	public List<User> findByNickName(String nickName) {
		return this.userRepositoy.findByNickName(nickName);
	}

	@Transactional
	public void delete(Long id) {
		userRepositoy.delete(id);
	}

	@Transactional
	public Iterable<User> getAll() {
		return userRepositoy.findAll();
	}
	
	public List<User> findAll() {
		return this.userRepositoy.findAll();
	}

	@Transactional
	public User getUserById(Long id) {
		return userRepositoy.findOne(id);
	}

	// 基于userId，查询用户信息
	public User findByUserId(long userId) {
		return this.userRepositoy.findByUserId(userId);
	}

	// 分页查询
	public Page<User> findAllUserBySort(Pageable pageable) {
		return this.userRepositoy.findAll(pageable);
	}

	// 查询当月新增用户
	public List<User> findByCreateTimeBetweenDate() {
		List<Date> dateList = DateFormatUtil.getStartDateAndEndDateOfNowMonth();
		return this.userRepositoy.findByCreateTimeBetween(dateList.get(0), dateList.get(1));
	}
	
	// 查询上一周新增用户数量
	public int[] findByCreateTimeBetweenWeek() {
		int[] countArr = new int[7];
		List<List<Date>> dateList = DateFormatUtil.getStartDayAndEndDayOfLastWeek();
		int i = 0;
		for(List<Date> list : dateList) {
			List<User> userList = this.userRepositoy.findByCreateTimeBetween(list.get(0), list.get(1));
			int count = userList.size();
			countArr[i++] = count;
		}
		
		return countArr;
	}
	
	public int findByCreateTimeBetweenNowDay() {
		List<Date> dateList = DateFormatUtil.getStartDateAndEndDateOfNowDay();
		List<User> userList = this.userRepositoy.findByCreateTimeBetween(dateList.get(0), dateList.get(1));
		return userList.size();
	}
	
	// 基于用户名或昵称，分页模糊查询
	public Page<User> findUsersByNickNameOrAccountLike(UserTypeQueryRequest userTypeQueryRequest, Pageable pageable) {
		String type = userTypeQueryRequest.getType();
		String value = userTypeQueryRequest.getValue();
		
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate p = null;
				
				if(type.equals("account")) {
					p = cb.like(root.get("account").as(String.class), "%" + value + "%");
				}else if(type.equals("nickName")) {
					p = cb.like(root.get("nickName").as(String.class), "%" + value + "%");
				}else {
					throw new RuntimeException("参数类型不正确");
				}
				
				return p;
			}

		};

		return this.userRepositoy.findAll(spec, pageable);
	}
	
}
