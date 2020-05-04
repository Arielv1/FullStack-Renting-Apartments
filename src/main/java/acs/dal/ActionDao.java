package acs.dal;

import org.springframework.data.repository.CrudRepository;

import acs.data.ActionEntity;
import acs.data.ActionIdEntity;

//Create Read Update Delete
public interface ActionDao extends CrudRepository<ActionEntity, ActionIdEntity>{
	
}
