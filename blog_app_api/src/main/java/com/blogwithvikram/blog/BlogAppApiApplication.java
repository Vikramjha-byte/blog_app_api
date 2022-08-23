package com.blogwithvikram.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogwithvikram.blog.config.AppConstants;
import com.blogwithvikram.blog.dao.RoleDao;
import com.blogwithvikram.blog.entities.Role;
import com.blogwithvikram.blog.payload.Postresponse;
import com.blogwithvikram.blog.services.FileService;
import com.blogwithvikram.blog.services.impl.FileServiceImpl;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleDao roleDao;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

	@Bean
	public Postresponse postresponse() {
		return new Postresponse();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(encoder.encode("Abc123"));

		try {
			
			Role admin= new Role();
			admin.setId(AppConstants.ADMIN_USER);
			admin.setName("ROLE_ADMIN");
			
			Role user= new Role();
			user.setId(AppConstants.NORMAL_USER);
			user.setName("ROLE_NORMAL");
			
			List<Role> roles= List.of(admin,user);
			List<Role> result = roleDao.saveAll(roles);
			
			result.forEach(r-> System.out.println(r.getName()));
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
