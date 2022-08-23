package com.blogwithvikram.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogwithvikram.blog.exceptions.*;
import com.blogwithvikram.blog.config.AppConstants;
import com.blogwithvikram.blog.dao.RoleDao;
import com.blogwithvikram.blog.dao.UserDao;
import com.blogwithvikram.blog.entities.Role;
import com.blogwithvikram.blog.entities.User;
import com.blogwithvikram.blog.payload.UserDto;
import com.blogwithvikram.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RoleDao roleDao;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userDao.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userDao.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User save = this.userDao.save(user);
		UserDto userToDto = this.userToDto(save);
		return userToDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User byId = this.userDao.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		;
		return this.userToDto(byId);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> findAll = userDao.findAll();
		List<UserDto> dtos1 = findAll.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return dtos1;
	}

	@Override
	public void deleteUser(Integer userId) {
		userDao.deleteById(userId);

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = mapper.map(userDto, User.class);
		// encoded the password
		user.setPassword(encoder.encode(user.getPassword()));
		// roles
		Role role = roleDao.findById(AppConstants.ADMIN_USER).get();
		user.getRoles().add(role);
		
		User newUser = userDao.save(user);
		return mapper.map(newUser, UserDto.class);
	}

	public User dtoToUser(UserDto userDto) {

		User user = mapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());

		return user;
	}

	public UserDto userToDto(User user) {

		UserDto dto = mapper.map(user, UserDto.class);
//		dto.setId(user.getId());
//		dto.setName(user.getName());
//		dto.setEmail(user.getEmail());
//		dto.setPassword(user.getPassword());
//		dto.setAbout(user.getAbout());
		return dto;
	}

}
