package acs.data.elements;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import acs.data.UserIdEntity;

/*
 "createdBy": {
    	"userid":{
    		"domain:"2020b.ofir.cohen",
        	"email": "ofir.cohen@gmail.com"
        	}
    }
 */
@Embeddable
public class CreatedByEntity {
	
	private UserIdEntity userId;
	
	public CreatedByEntity() {
		
	}

	public CreatedByEntity(UserIdEntity userId) {
		super();
		this.userId = userId;
	}
	
	@Embedded
	public UserIdEntity getUserId() {
		return userId;
	}

	public void setUserId(UserIdEntity userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CreatedByEntity [userId=" + userId + "]";
	}
	
	
}
