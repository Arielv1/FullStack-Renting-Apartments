package acs.logic.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import acs.data.UserEntity;
import acs.data.UserRole;
import acs.logic.user.UserConvertor;
import acs.logic.user.UserServiceInterface;
import acs.rest.users.UserBoundary;

@Service
public class UserService implements UserServiceInterface {

	private String ProjectName;
	private Map<Object, UserEntity> dataBase;
	private UserConvertor convert;
	
	
	@Autowired
	public UserService(UserConvertor convert) {
		this.convert = convert;
	}
	

	@Value("${spring.application.name:ofir.cohen}")
	public void setProjectName(String ProjectName) {
		this.ProjectName = ProjectName;
	}

	@PostConstruct
	public void init() {
		System.err.println("project name: " + this.ProjectName);
		this.dataBase = Collections.synchronizedMap(new HashMap<>());
	}

	// create new user
	@Override
	public UserBoundary createUser(UserBoundary user) {
		UserEntity entity = this.dataBase.get(user.getUserId());
		if (entity != null) {
			throw new RuntimeException("the User alreay exsite in the data base!!");
		} else {
			entity = convert.toEntity(user);
			this.dataBase.put(user.getUserId(), entity);
		return	user;
		}

	}

	// login to user in the data base
	@Override
	public UserBoundary login(String domain, String email) {
		Map<String, Object> userId = new HashMap<>();
		userId.put("domain", domain);
		userId.put("email", email);
		UserEntity entity = this.dataBase.get(userId);
		if (entity != null) {
			return convert.fromEntity(entity);
		} else {
			throw new RuntimeException("the login action failed! please check the user details again");
		}

	}

	//update user details in the data base
	@Override
	public UserBoundary updateUser(String domain, String email, UserBoundary update) {
		Map<String, Object> userId = new HashMap<>();
		userId.put("domain", domain);
		userId.put("email", email);
		UserEntity entity = this.dataBase.get(userId);
		if (entity != null) {
			entity.setUserName(update.getUserName());
			entity.setRole(update.getRole());
			entity.setAvatar(update.getAvatar());
			return convert.fromEntity(entity);
		} else {
			throw new RuntimeException("invalid user details");
		}
	}


	// get all users from the data base
	@Override
	public UserBoundary[] getAllUsers(String adminDomain, String adminEmail) {
		Map<String, Object> userId = new HashMap<>();
		userId.put("domain", adminDomain);
		userId.put("email", adminEmail);
		UserEntity entity = this.dataBase.get(userId);
		if (entity.getRole().equals(UserRole.ADMIN)) {
			return IntStream.range(0, this.dataBase.size()).mapToObj(i -> "User # " + i)
					.map(user -> new UserBoundary(Collections.singletonMap("domain", "ofir.cohen"), "ofir choen",
							UserRole.PLAYER, "?!?"))
					.collect(Collectors.toList()).toArray(new UserBoundary[0]);

		} else {
			throw new RuntimeException("admin details invalid");
		}
	}

	// delete all users from the data base
	@Override
	public void deleteAllUsers(String adminDomain, String adminEmail) {
		Map<String, Object> userId = new HashMap<>();
		userId.put("domain", adminDomain);
		userId.put("email", adminEmail);
		UserEntity entity = this.dataBase.get(userId);
		if (entity.getRole().equals(UserRole.ADMIN)) {
			this.dataBase.clear();
		} else {
			throw new RuntimeException("admin details invalid");
		}

	}
}
