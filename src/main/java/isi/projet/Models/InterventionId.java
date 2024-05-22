package isi.projet.Models;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Embeddable
@Data
public class InterventionId implements Serializable{
	
    private static final long serialVersionUID = 1L;
    
    private String moduleId;
    private String enseigantId;
	public InterventionId(String moduleId, String enseigantId) {
		super();
		this.moduleId = moduleId;
		this.enseigantId = enseigantId;
	}
	public InterventionId() {
		super();
	}

}
