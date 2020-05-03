package acs.dal;

import org.springframework.data.repository.CrudRepository;

import acs.data.UserEntity;
import acs.data.UserIdEntity;

public interface UserDao extends CrudRepository<UserEntity, UserIdEntity>{

}
