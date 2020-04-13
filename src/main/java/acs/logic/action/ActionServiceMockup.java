package acs.logic.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import acs.data.ActionEntity;
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
	@Value("${spring.application.name:demo}")
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActionBoundary> getAllActions(String adminDomain, String adminEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllActions(String adminDomain, String adminEmail) {
		// TODO Auto-generated method stub

	}

}
