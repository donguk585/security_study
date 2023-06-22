package com.study.security_donguk.domain.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
	public int save(User user) throws Exception;
	public User findUserByUsername(String username) throws Exception;
}
