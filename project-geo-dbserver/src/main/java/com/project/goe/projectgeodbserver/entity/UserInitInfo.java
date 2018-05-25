package com.project.goe.projectgeodbserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_userinitcheck")
public class UserInitInfo {
	
	@Id
	@Column
	private String str;
	
	@Column
	private int strcount;

	

	public int getStrcount() {
		return strcount;
	}

	public void setStrcount(int strcount) {
		this.strcount = strcount;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	
	

}
