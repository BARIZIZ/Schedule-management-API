package isi.projet.Models;

import lombok.Data;

@Data
public class EnseignantModuleDTO {
    private String email;
    private String nom;
    private String prenom;
    private String modules;
    private Long heures;
}
