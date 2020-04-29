package acs.dal;

import org.springframework.data.repository.CrudRepository;

import acs.data.ElementIdEntity;
import acs.data.elements.ElementEntity;

												
public interface ElementDao extends CrudRepository<ElementEntity, ElementIdEntity>{

}
