package com.project.goe.projectgeodbserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserLoginSetting {
	private long expireTime;
	
	public long getExpireTime() {
		return expireTime;
	}

	@Value("${login.expireTime}")
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

}
