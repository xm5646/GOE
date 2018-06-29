package com.project.goe.projectgeodbserver.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.project.goe.projectgeodbserver.viewentity.RetMsg;

public class ValidateErrorUtil {
	private ValidateErrorUtil() {
	}

	private static ValidateErrorUtil validateErrorUtil = new ValidateErrorUtil();

	public static ValidateErrorUtil getInstance() {
		return validateErrorUtil;
	}

	public RetMsg errorList(BindingResult bindingResult) {
		if (!bindingResult.hasErrors())
			return null;

		List<ObjectError> errors = bindingResult.getAllErrors();

		List<String> errorList = new ArrayList<String>();
		for (ObjectError error : errors) {
			errorList.add(error.getDefaultMessage());
		}

		RetMsg retMsg = new RetMsg();
		retMsg.setCode(400);
		retMsg.setData(errorList);
		if (errorList.size()>0) {
			retMsg.setMessage(errorList.get(0));
		} else {
			retMsg.setMessage("表单数据格式不正确!");
		}
		retMsg.setSuccess(false);

		return retMsg;
	}

}
