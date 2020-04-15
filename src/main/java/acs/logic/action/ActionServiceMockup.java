package acs.logic.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import acs.data.ActionEntity;
import acs.data.ElementEntity;
import acs.rest.action.ActionBoundary;
import acs.rest.element.ElementBoundary;

@Service
public class ActionServiceMockup implements ActionService {
	private String projectName;
	private Map<String, ActionEntity> database;
	private ActionConverter converter;

	@Autowired
	public ActionServiceMockup(ActionConverter converter) {
		this.converter = converter;
	}

	// inject value from configuration or use default value
	@Value("${spring.application.name:ofir.cohen}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@PostConstruct
	public void init() {
		// initialize object after injection
		System.err.println("project name: " + this.projectName);

		// make sure that this is actually the proper Map for this application
		this.database = Collections.synchronizedMap(new HashMap<>());
	}
	

	@Override
	public Object invokeAction(ActionBoundary action) {
		
		ActionEntity actionEntity = this.converter.toEntity(action);
		
		Map <String, Object> actionId = new HashMap<>();;
		
		String id = UUID.randomUUID().toString();
		
		actionId.put(id, actionEntity);
		
		actionEntity.setActionId(actionId);
		
		actionEntity.setCreatedTimestamp(new Date());
		
		this.database.put(id, actionEntity);

		return this.converter.fromEntity(actionEntity);
	
	}

	@Override
	public List<ActionBoundary> getAllActions(String adminDomain, String adminEmail) {
		List<ActionBoundary> allActionsBoundaries = new ArrayList <ActionBoundary>();
		List<ActionEntity> allActionsEntities = new ArrayList <ActionEntity> (this.database.values());
		
		for (ActionEntity entity : allActionsEntities) 
			allActionsBoundaries.add(this.converter.fromEntity(entity));
	
		return allActionsBoundaries;
	}

	@Override
	public void deleteAllActions(String adminDomain, String adminEmail) {
		this.database.clear();
	}

}
