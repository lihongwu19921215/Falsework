package com.sino.scaffold.controller;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sino.scaffold.biz.UserService;
import com.sino.scaffold.biz.base.Pager;
import com.sino.scaffold.model.Status;
import com.sino.scaffold.model.User;

import tk.mybatis.mapper.entity.Example;

/**
 * @author kerbores
 *
 */
@RestController
public class IndexController {

	@Autowired
	UserService userService;

	@GetMapping("/")
	public String index() {
		return "Hello sino soft!";
	}

	@GetMapping("list")
	public List<User> list() {
		return userService.listAll();
	}

	@GetMapping("add")
	public User add() {
		User user = new User();
		user.setStatus(RandomUtils.nextInt(0, 2) == 1 ? Status.ENABLED : Status.DISABLED);
		user.setUserName(String.format("sino%d", RandomUtils.nextInt(0, Integer.MAX_VALUE)));
		user.setEmail(String.format("test%d@sinosoft.com.cn", RandomUtils.nextLong(0, Long.MAX_VALUE)));
		userService.save(user);
		return user;
	}

	@GetMapping("delete/{id}")
	public int delete(@PathVariable("id") long id) {
		return userService.deleteById(id);
	}

	@GetMapping("page/{page}")
	public Pager<User> pagerAll(@PathVariable("page") int page) {
		return userService.listByPage(page, 10);
	}
	
	@GetMapping("search/{key}/{page}")
	public Pager<User> pagerSearch(@PathVariable("key")String key,@PathVariable("page") int page) {
		Example example = new Example(User.class);
		example.createCriteria().andLike("userName", String.format("%%%s%%", key));
		return userService.listByPage(page, 10,example);
	}

}
