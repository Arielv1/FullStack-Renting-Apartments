package acs.logic.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acs.dal.ActionDao;
import acs.data.ActionEntity;
import acs.data.ActionIdEntity;
import acs.data.ElementIdEntity;
import acs.data.UserIdEntity;
import acs.data.actions.ActionElementEntity;
import acs.data.actions.InvokedByEntity;
import acs.logic.action.ActionConverter;
import acs.logic.action.ActionService;
import acs.rest.action.ActionBoundary;
import acs.rest.utils.ValidEmail;

@Service
public class DbActionService implements ActionService {
	private String projectName;
	private ActionDao actionDao;
	private ActionConverter converter;

	@Autowired
	public DbActionService(ActionDao actionDao, ActionConverter converter) {
		this.actionDao = actionDao;
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
	}

	@Override
	@Transactional // (readOnly = false)
	public Object invokeAction(ActionBoundary action) {
		if (action.getType() == null) {
			throw new RuntimeException("Type can not be null");
		}

		String id = UUID.randomUUID().toString();

		ActionEntity entity = this.converter.toEntity(action);

		entity.setActionId(new ActionIdEntity(this.projectName, id));

		entity.setCreatedTimestamp(new Date());

		entity.setInvokedBy(new InvokedByEntity(new UserIdEntity(action.getInvokedBy().getUserId().getDomain(),
				action.getInvokedBy().getUserId().getEmail())));

		entity.setElement(new ActionElementEntity(new ElementIdEntity(action.getElement().getElementId().getDomain(),
				action.getElement().getElementId().getId())));

		return this.converter.fromEntity(this.actionDao.save(entity)); // SELECT +INSERT / UPDATE

	}

	@Override
	@Transactional(readOnly = true)
	public List<ActionBoundary> getAllActions(String adminDomain, String adminEmail) {
		// INVOKE SELECT DATABASE
		return StreamSupport.stream(this.actionDao.findAll().spliterator(), false) // Stream<ActionEntity>
				.map(this.converter::fromEntity)// Stream<ActionBoundary>
				.collect(Collectors.toList()); // List<ActionBoundary>
	}

	@Override
	@Transactional
	public void deleteAllActions(String adminDomain, String adminEmail) {
		// INVOKE DELETE DATABASE: DELETE
		this.actionDao.deleteAll(); // DELETE

	}

}
