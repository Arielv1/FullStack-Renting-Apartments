package acs.logic.action;

import org.springframework.stereotype.Component;

import acs.data.ActionEntity;
import acs.rest.action.ActionBoundary;

@Component
public class ActionConverter {
	public ActionBoundary fromEntity(ActionEntity entity) {
		return new ActionBoundary(entity.getActionId(), entity.getType(), entity.getElement(),
				entity.getCreatedTimestamp(), entity.getInvokedBy(), entity.getActionAttributes());
	}

	public ActionEntity toEntity(ActionBoundary boundary) {
		ActionEntity entity = new ActionEntity();
		entity.setActionId(boundary.getActionId());

		if (boundary.getType() != null) {
			entity.setType(boundary.getType());
		} else {
			entity.setType("search"); // we need to decide the default type
		}
		if (boundary.getElement() != null) {
			entity.setElement(boundary.getElement());
		} else {

		}
		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		if (boundary.getInvokedBy() != null) {
			entity.setInvokedBy(boundary.getInvokedBy());
		} else {

		}
		entity.setActionAttributes(boundary.getActionAttributes());
		return entity;
	}
}
