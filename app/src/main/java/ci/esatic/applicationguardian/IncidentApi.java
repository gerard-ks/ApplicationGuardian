package ci.esatic.applicationguardian;

public class IncidentApi {
    private Integer id_Statut_Incident = 1;
    private Integer  id_Priorite_Incident, id_Utilisateur, statut;
    private String description_Incident, message;

    public IncidentApi(Integer id_Statut_Incident, Integer id_Priorite_Incident, Integer id_Utilisateur, String description_Incident) {
        this.id_Statut_Incident = id_Statut_Incident;
        this.id_Priorite_Incident = id_Priorite_Incident;
        this.id_Utilisateur = id_Utilisateur;
        this.statut = statut;
        this.description_Incident = description_Incident;
    }


    public Integer getStatut() {
        return statut;
    }

    public String getMessage() {
        return message;
    }
}
