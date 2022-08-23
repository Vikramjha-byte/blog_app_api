package com.blogwithvikram.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogwithvikram.blog.dao.UserDao;
import com.blogwithvikram.blog.entities.User;
import com.blogwithvikram.blog.exceptions.ResourceNotFoundException;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database by username

		User user = userDao.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", username));

		return user;
	}

}
