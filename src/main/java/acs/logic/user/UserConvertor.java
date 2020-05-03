package acs.logic.user;

import org.springframework.stereotype.Component;

import acs.data.UserEntity;
import acs.data.UserIdEntity;
import acs.rest.users.UserBoundary;
import acs.rest.utils.UserIdBoundary;
import acs.rest.utils.UserNameBoundray;

@Component
public class UserConvertor {
	
	public UserBoundary fromEntity(UserEntity entity) {
		UserBoundary boundray;
		String[] nameBoundry = entity.getUserName().split(" ");
		UserIdBoundary userId = new UserIdBoundary(entity.getUserId().getDomain(), entity.getUserId().getEmail());
		if(nameBoundry.length != 1) {
		 boundray = new UserBoundary(userId, new UserNameBoundray(nameBoundry[0], nameBoundry[1]), 
				entity.getRole(), entity.getAvatar());
		}else {
			 boundray = new UserBoundary(userId, new UserNameBoundray(nameBoundry[0], null), 
					entity.getRole(), entity.getAvatar());
		}
		return boundray;
		
	}
	public UserEntity toEntity(UserBoundary boundray) {
		UserEntity entity;
		String firstName = boundray.getUserName().getFirst();
		String lastName = boundray.getUserName().getLast();
		UserIdEntity userId = new UserIdEntity(boundray.getUserId().getDomain(), boundray.getUserId().getEmail());
			entity = new UserEntity(userId, firstName+" "+lastName, boundray.getRole(),
				boundray.getAvatar());
		

		return entity;
	}

}
