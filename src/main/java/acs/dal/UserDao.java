package acs.dal;


import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import acs.data.users.UserEntity;
import acs.data.utils.UserIdEntity;

public interface UserDao extends PagingAndSortingRepository<UserEntity, UserIdEntity>{
	/*
	public List<UserEntity> findByUserId(
			@Param ("userId") UserIdEntity userId, 
			Pageable pageable);
		*/
	
	

}
