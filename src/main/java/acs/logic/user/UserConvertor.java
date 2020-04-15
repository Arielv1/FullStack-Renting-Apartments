package acs.logic.user;

import org.springframework.stereotype.Component;

import acs.data.UserEntity;
import acs.rest.users.UserBoundary;

@Component
public class UserConvertor {
	
	public UserBoundary fromEntity(UserEntity entity) {
		UserBoundary boundray = new UserBoundary(entity.getUserId(), entity.getUserName(), 
				entity.getRole(), entity.getAvatar());
		return boundray;
		
	}
	public UserEntity toEntity(UserBoundary boundray) {
		UserEntity entity = new UserEntity(boundray.getUserId(), boundray.getRole() ,boundray.getUserName()
				,boundray.getAvatar());
		return entity;
		
	}

}
