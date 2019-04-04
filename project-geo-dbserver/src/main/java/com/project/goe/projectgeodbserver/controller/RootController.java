package com.project.goe.projectgeodbserver.controller;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.RedisService;
import com.project.goe.projectgeodbserver.service.TestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class RootController {



	@RequestMapping(value = "/api")
	public String setStr() {
		return "API Server is running";
	}


}
