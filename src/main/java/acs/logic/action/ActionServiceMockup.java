package acs.logic.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import acs.data.ActionEntity;
import acs.data.ActionIdEntity;
import acs.data.ElementIdEntity;
import acs.data.UserIdEntity;
import acs.data.actions.InvokedByEntity;
import acs.rest.action.ActionBoundary;

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

		String id = UUID.randomUUID().toString();

		ActionEntity entity = this.converter.toEntity(action);

		entity.setActionId(new ActionIdEntity (this.projectName,id));

		entity.setCreatedTimestamp(new Date());

		entity.setInvokedBy(new InvokedByEntity(new UserIdEntity(action.getInvokedBy().getUserId().getDomain(),
				action.getInvokedBy().getUserId().getEmail())));

		this.database.put(id, entity);

		return this.converter.fromEntity(entity);

	}

	@Override
	public List<ActionBoundary> getAllActions(String adminDomain, String adminEmail) {
		return this.database.values() // Collection<ActionEntity>
				.stream() // Stream<ActionEntity>
				.map(this.converter::fromEntity) // Stream<ActionBoundary>
				.collect(Collectors.toList()); // List<ActionBoundary>
	}

	@Override
	public void deleteAllActions(String adminDomain, String adminEmail) {
		// TODO - check admin privileges
		this.database.clear();
	}

}
