package acs.logic.action;

import org.springframework.stereotype.Component;

import acs.data.ActionEntity;
import acs.rest.action.ActionBoundary;

@Component
public class ActionConverter {
	public ActionBoundary entityToBoundary(ActionEntity entity) {
		if (entity == null) 
			return null;
		return new ActionBoundary(entity.getActionId(), entity.getType(), entity.getElement(),
				entity.getCreatedTimestamp(), entity.getInvokedBy(), entity.getActionAttributes());
	}

	public ActionEntity boundaryToEntity(ActionBoundary boundary) {
		ActionEntity entity = new ActionEntity();
		entity.setActionId(boundary.getActionId());

		if (boundary.getType() != null) {
			entity.setType(boundary.getType());
		} else
			throw new RuntimeException("ActionBoundary invalid type");

		if (boundary.getElement() != null) {
			entity.setElement(boundary.getElement());
		} else throw new RuntimeException("ActionBoundary invalid element");

		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		if (boundary.getInvokedBy() != null) {
			entity.setInvokedBy(boundary.getInvokedBy());
		} else throw new RuntimeException("ActionBoundary invalid invokedby");


		entity.setInvokedBy(boundary.getInvokedBy());

		entity.setActionAttributes(boundary.getActionAttributes());
		return entity;
	}
}
