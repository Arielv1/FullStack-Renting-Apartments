package acs.logic.action;

import org.springframework.stereotype.Component;

import acs.data.ActionEntity;
import acs.rest.action.ActionBoundary;
@Component
public class ActionConverter {
	public ActionBoundary fromEntity (ActionEntity entity) {
		return new ActionBoundary(
				entity.getActionId(),
				entity.getType(),
				entity.getElement(),
				entity.getCreatedTimeStamp(),
				entity.getInvokedBy(),
				entity.getActionAttributes()
				);
	}
	
	
	public ActionEntity toEntity(ActionBoundary boundary) {
		ActionEntity entity = new ActionEntity();
		entity.setActionId(boundary.getActionId());
		entity.setType(boundary.getType());
		entity.setElement(boundary.getElement());
		entity.setCreatedTimeStamp(boundary.getCreatedTimestamp());
		entity.setInvokedBy(boundary.getInvokedBy());
		entity.setActionAttributes(boundary.getActionAttributes());
		return entity;
	}
}


