package com.ex.oa.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ex.oa.admin.entry.User;
import com.ex.oa.admin.server.UserServer;

@Controller
@RequestMapping(value = "user")
public class UserAction {

	@Resource(name="userServer")
	private UserServer userServer;
	
	@RequestMapping(value = "add")
	public ModelAndView addUser(HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();	
		
		User user = new User();
		
		user.setId(11);
		user.setName("qwer");
		user.setPassword("qwer");
		
		map.put("result", userServer.insert(user)+" == 添加用户信息");
		
		return new ModelAndView("index",map);
	}

}
