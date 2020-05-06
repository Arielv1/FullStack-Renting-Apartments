package acs.dal;

import org.springframework.data.repository.CrudRepository;

import acs.data.users.UserEntity;
import acs.data.utils.UserIdEntity;

public interface UserDao extends CrudRepository<UserEntity, UserIdEntity>{

}
