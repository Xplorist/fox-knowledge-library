package com.foxconn.helloworld.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author C3005579
 * @date 2019年3月13日 上午11:23:36 
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController {

	@RequestMapping("/world.x")
	public String world(@RequestParam("file") MultipartFile file) {
		System.out.println(file.getOriginalFilename());
		
		return "";
	}
}


