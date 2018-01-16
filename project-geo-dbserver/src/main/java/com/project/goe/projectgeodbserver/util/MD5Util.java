package com.project.goe.projectgeodbserver.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.Base64Utils;

public class MD5Util {
	private MD5Util() {}
	//加密
	public static String encrypeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5=MessageDigest.getInstance("MD5");
       
        String encrypeStr = Base64Utils.encodeToString(md5.digest(str.getBytes("utf-8")));
        return encrypeStr;
    }
	
	public static void main(String[] args) throws Exception {
		System.out.println(encrypeByMd5("admin"));
	}
	
}
