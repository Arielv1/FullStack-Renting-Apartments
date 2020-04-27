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
		UserEntity entity;
		String[] nameInDataBase = boundray.getUserName().split(" ");
		if(nameInDataBase.length != 1) {
			entity = new UserEntity(boundray.getUserId(), boundray.getRole(),
				nameInDataBase[0] +" "+ nameInDataBase[1], boundray.getAvatar());
		}else {
			 entity = new UserEntity(boundray.getUserId(), boundray.getRole(),
					nameInDataBase[0], boundray.getAvatar());
		}

		return entity;
	}

}
