package isi.projet.Models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name="intervention")
public class Intervention {
	@EmbeddedId
	private InterventionId interventionId;
	@ManyToOne
	@MapsId("moduleId")
	private Modules module;
	
    @ManyToOne
    @MapsId("enseigantId")
    private Enseignant enseignant;
    

    private Long VHCoursInter;
	private Long VHtdInter;
	private Long VHtpInter;
	private Long EvaluationInter;

}
