package isi.projet.Models;

import lombok.Data;

@Data
public class InterventionDTO {
    private String email;
    private String intitule;
    private Long vhcoursInter;
    private Long vhtdInter;
    private Long evaluationInter;
    private Long vhtpInter;

    // Getters et setters
}
