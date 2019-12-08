package com.wzp.test;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wzp.entity.User;

import utils.RandomUtil;
import utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-redis.xml")
public class RedisTest {
	@Resource
	private RedisTemplate redisTemplate;
	@Test
	public void userJDK() {
		List<User> listUser = new ArrayList<>();
		for (int i = 1; i <= 50000; i++) {
			String name = StringUtil.randomChineseString(3);
			String phone = "13"+RandomUtil.randomString(9);
			User user =new User(i, name, phone);
			listUser.add(user);
		}
		ListOperations opsForList = redisTemplate.opsForList();
		
		long start = System.currentTimeMillis();
		
		opsForList.leftPushAll("user_jdk", listUser);
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	@Test
	public void userJSON() {
		List<User> listUser = new ArrayList<>();
		for (int i = 1; i <= 50000; i++) {
			String name = StringUtil.randomChineseString(3);
			String phone = "13"+RandomUtil.randomString(9);
			User user =new User(i, name, phone);
			listUser.add(user);
		}
		ListOperations opsForList = redisTemplate.opsForList();
		
		long start = System.currentTimeMillis();
		
		opsForList.leftPushAll("user_json", listUser);
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	@Test
	public void userHash() {
		Map< String, User> map = new HashMap<String, User>();
		for (int i = 1; i <= 50000; i++) {
			String name = StringUtil.randomChineseString(3);
			String phone = "13"+RandomUtil.randomString(9);
			User user =new User(i, name, phone);
			map.put(i+"", user);
		}
		HashOperations opsForHash = redisTemplate.opsForHash();
		
		long start = System.currentTimeMillis();
		
		opsForHash.putAll("user_hash", map);
		
		long end = System.currentTimeMillis();
		
		System.out.println(end-start);
	}
}
