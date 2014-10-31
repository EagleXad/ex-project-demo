package com.ex.oa.admin.server.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ex.oa.admin.dao.UserMapper;
import com.ex.oa.admin.entry.User;
import com.ex.oa.admin.server.UserServer;

@Repository(value = "userServer")
@Transactional
public class UserServerImpl implements UserServer {

	@Resource(name="userMapper")
	private UserMapper userMapper;

	@Override
	public int insert(User user) {

		int result = userMapper.insert(user);

		return result;
	}

}
