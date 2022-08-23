package com.blogwithvikram.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwithvikram.blog.entities.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {

}
