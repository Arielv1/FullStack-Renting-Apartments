package acs.logic.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import acs.data.UserEntity;
import acs.data.UserRole;
import acs.rest.users.UserBoundary;
import acs.rest.utils.UserIdBoundary;

@Service
public class UserServiceMockup implements UserService {

	private String ProjectName;
	private Map<Object, UserEntity> dataBase;
	private UserConvertor convert;

	@Autowired
	public UserServiceMockup(UserConvertor convert) {
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
		user.getUserId().setDomain(this.ProjectName);
		UserEntity entity = this.dataBase.get(user.getUserId());
		if (entity != null) {
			throw new RuntimeException("the User alreay exsite in the data base!!");
		} else {
			if (user.getRole() != null) {
				entity = convert.toEntity(user);
				this.dataBase.put(user.getUserId(), entity);
				return convert.fromEntity(entity);
			} else {
				throw new RuntimeException("the role is not from UserRole type");
			}
		}

	}

	// login to user in the data base
	@Override
	public UserBoundary login(String domain, String email) {
		UserIdBoundary userId = new UserIdBoundary(domain, email);
		UserEntity entity = this.dataBase.get(userId);
		if (entity != null) {
			return convert.fromEntity(entity);
		} else {
			throw new RuntimeException("the login action failed! please check the user details again");
		}

	}

	// update user details in the data base
	@Override
	public UserBoundary updateUser(String domain, String email, UserBoundary update) {
		UserIdBoundary userId = new UserIdBoundary(domain, email);
		UserEntity entity = this.dataBase.get(userId);
		if (entity != null) {
			entity.setUserName(update.getUserName().getFirst() +" "+ update.getUserName().getLast());
			entity.setRole(update.getRole());
			entity.setAvatar(update.getAvatar());
			return convert.fromEntity(entity);
		} else {
			throw new RuntimeException("invalid user details");
		}
	}

	// get all users from the data base
	@Override
	public List<UserBoundary> getAllUsers(String adminDomain, String adminEmail) {
		UserIdBoundary userId = new UserIdBoundary(adminDomain, adminEmail);
		UserEntity entity = this.dataBase.get(userId);
		if (entity.getRole().equals(UserRole.ADMIN)) {
			return this.dataBase.values().stream().map(this.convert::fromEntity).collect(Collectors.toList());
		} else {
			throw new RuntimeException("admin details invalid");
		}
	}

	// delete all users from the data base
	@Override
	public void deleteAllUsers(String adminDomain, String adminEmail) {
		/*
		 * Map<String, Object> userId = new HashMap<>(); userId.put("domain",
		 * adminDomain); userId.put("email", adminEmail); UserEntity entity =
		 * this.dataBase.get(userId); if (entity.getRole().equals(UserRole.ADMIN)) {
		 */
		this.dataBase.clear();

		// } else {
		// throw new RuntimeException("admin details invalid");
		// }

	}

	public Map<Object, UserEntity> getDataBase() {
		return dataBase;
	}

	public void setDataBase(Map<Object, UserEntity> dataBase) {
		this.dataBase = dataBase;
	}

	public String getProjectName() {
		return ProjectName;
	}

}
