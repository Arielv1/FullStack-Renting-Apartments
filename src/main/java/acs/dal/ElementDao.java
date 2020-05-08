package acs.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import acs.data.elements.ElementEntity;
import acs.data.utils.ElementIdEntity;											
public interface ElementDao extends PagingAndSortingRepository<ElementEntity, ElementIdEntity>{
	
	public List<ElementEntity> findAllByName(
			@Param("name") String name,
			Pageable pageable); 
	
	// select * where type is ?
	public List<ElementEntity> findAllByType(
			@Param("type") String type,
			Pageable pageable); 
	
	public List<ElementEntity> findAllChildrenByParent_ElementId(
			@Param("parentId") ElementIdEntity parentId, 
			Pageable pageable);
	
	public List<ElementEntity> findAllParentsByChildren_ElementId(
			@Param("parentId") ElementIdEntity parentId, 
			Pageable pageable);
	
	
	// select * where lat+
	public List<ElementEntity> findAllBylocationLatBetweenAndLocationLngBetween(
			@Param("lat1") Double lat1,
			@Param("lat2") Double lat2,
			@Param("lng1") Double lng1,
			@Param("lng2") Double lng2,
			Pageable pageable);
	 
}
