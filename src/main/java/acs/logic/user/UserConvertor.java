package acs.logic.user;

import org.springframework.stereotype.Component;

import acs.data.UserEntity;
import acs.data.UserIdEntity;
import acs.data.UserNameEntity;
import acs.rest.users.UserBoundary;
import acs.rest.utils.UserIdBoundary;
import acs.rest.utils.UserNameBoundray;

@Component
public class UserConvertor {
	
	public UserBoundary fromEntity(UserEntity entity) {
		UserBoundary boundray;
		UserIdBoundary userId = new UserIdBoundary(entity.getUserId().getDomain(), entity.getUserId().getEmail());
		UserNameBoundray userName = new UserNameBoundray(entity.getUserName().getFirst(), entity.getUserName().getLast());
		 boundray = new UserBoundary(userId, userName, 
				entity.getRole(), entity.getAvatar());
		
		return boundray;
		
	}
	public UserEntity toEntity(UserBoundary boundray) {
		UserEntity entity;
		UserIdEntity userId = new UserIdEntity(boundray.getUserId().getDomain(), boundray.getUserId().getEmail());
		UserNameEntity name = new UserNameEntity(boundray.getUserName().getFirst(), boundray.getUserName().getLast());
			entity = new UserEntity(userId, name, boundray.getRole(),
				boundray.getAvatar());
		

		return entity;
	}

}
