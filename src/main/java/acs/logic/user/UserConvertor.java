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
		String[] nameInDataBase = boundray.getUserName().split(" ");
		UserEntity entity = new UserEntity(boundray.getUserId(), boundray.getRole(),
				nameInDataBase[0] +" "+ nameInDataBase[1], boundray.getAvatar());

		return entity;
	}

}
