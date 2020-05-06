package acs.logic.user;

import org.springframework.stereotype.Component;

import acs.data.UserEntity;
import acs.data.UserIdEntity;
import acs.rest.users.UserBoundary;
import acs.rest.utils.UserIdBoundary;


@Component
public class UserConvertor {
	
	public UserBoundary fromEntity(UserEntity entity) {
		UserBoundary boundray;
		UserIdBoundary userId = new UserIdBoundary(entity.getUserId().getDomain(), entity.getUserId().getEmail());
		 boundray = new UserBoundary(userId, entity.getUsername(), 
				entity.getRole(), entity.getAvatar());
		
		return boundray;
		
	}
	public UserEntity toEntity(UserBoundary boundray) {
		String[] name = boundray.getUsername().split(" ");
		UserEntity entity;
		UserIdEntity userId = new UserIdEntity(boundray.getUserId().getDomain(), boundray.getUserId().getEmail());
		if(name.length != 1) {
			entity = new UserEntity(userId, name[0] +" "+name[1], boundray.getRole(),
					boundray.getAvatar());
		}else {
			entity = new UserEntity(userId, name[0], boundray.getRole(),
					boundray.getAvatar());
		}
			
		

		return entity;
	}

}
