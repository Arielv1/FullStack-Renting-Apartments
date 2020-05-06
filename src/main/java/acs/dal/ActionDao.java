package acs.dal;

import org.springframework.data.repository.CrudRepository;

import acs.data.actions.ActionEntity;
import acs.data.utils.ActionIdEntity;

//Create Read Update Delete
public interface ActionDao extends CrudRepository<ActionEntity, ActionIdEntity>{
	
}
