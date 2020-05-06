package acs.dal;

import org.springframework.data.repository.CrudRepository;

import acs.data.elements.ElementEntity;
import acs.data.utils.ElementIdEntity;

												
public interface ElementDao extends CrudRepository<ElementEntity, ElementIdEntity>{

}
