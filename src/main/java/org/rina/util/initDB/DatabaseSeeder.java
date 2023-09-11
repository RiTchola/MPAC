package org.rina.util.initDB;

import org.rina.dao.*;


import org.rina.enums.TypePersonne;
import org.rina.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;


@Component
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner initTestDatebase(
            IActiviteJpaDao iActiviteJpaDao,
            ICommuniqueJpaDao iCommuniqueJpaDao,
            IEvenementJpaDao iEvenementJpaDao,
            IFichierJpaDao iFichierJpaDao,
            IMedecinTraitantJpaDao iMedecinTraitantJpaDao,
            IMeetUpJpaDao iMeetUpJpaDao,
            IMenuJpaDao iMenuJpaDao,
            IPersonneContactJpaDao iPersonneContactJpaDao,
            IRapportQuotidienJpaDao iRapportQuotidienJpaDao,
            IRapportVisiteJpaDao iRapportVisiteJpaDao,
            IResidentJpaDao iResidentJpaDao
    ){
        return args -> {
            // Pour Activite
            /* if(false){
                for (int i = 1; i <= 10; i++) {
                    Activite activite = new Activite();
                    activite.setDate(new Date());
                    activite.setNom("Nom Activité " + i);
                    activite.setDescription("Description Activité " + i);
                    iActiviteJpaDao.save(activite);
                }

                // Pour Communique
                for (int i = 1; i <= 10; i++) {
                    Communique communique = new Communique();
                    communique.setDate(new Date());
                    communique.setContenu("Contenu Communique " + i);
                    iCommuniqueJpaDao.save(communique);
                }

                // Pour Evenement
                for (int i = 1; i <= 10; i++) {
                    Evenement evenement = new Evenement();
                    evenement.setDateEvent(new Date());
                    evenement.setDescription("Description Evenement " + i);
                    iEvenementJpaDao.save(evenement);
                }

                // Pour Fichier
                for (int i = 1; i <= 10; i++) {
                    Fichier fichier = new Fichier();
                    fichier.setNomFichier("Fichier " + i + ".txt");
                    fichier.setTypeF("TXT");
                    fichier.setDate(new Date());
                    fichier.setFileURL("http://example.com/fichier" + i + ".txt");
                    iFichierJpaDao.save(fichier);
                }

                // Pour MedecinTraitant
                for (int i = 1; i <= 10; i++) {
                    MedecinTraitant medecin = new MedecinTraitant();
                    medecin.setNom("Nom Medecin " + i);
                    medecin.setPrenom("Prenom Medecin " + i);
                    medecin.setEmail("medecin" + i + "@exemple.com");
                    medecin.setTel1("+1234567890");
                    medecin.setTel2("+0987654321");
                    medecin.setAdresse("Adresse " + i + ", Ville");
                    iMedecinTraitantJpaDao.save(medecin);
                }

                // Pour MeetUp
                for (int i = 1; i <= 10; i++) {
                    MeetUp meetup = new MeetUp();
                   meetup.setTypeA("TypeA " + i);
                    meetup.setMotif("Motif " + i);
                    meetup.setDate(new Date());
                    meetup.setNomResident("Nom Resident " + i);
                    meetup.setPrenomResident("Prenom Resident " + i);
                    meetup.setDateNaissanceR(new Date());
                    meetup.setNbrParticipants(i);
                    meetup.setEtat("Etat " + i);
                    iMeetUpJpaDao.save(meetup);
                }

                // Pour Menu
                for (int i = 1; i <= 10; i++) {
                    Menu menu = new Menu();
                    menu.setSemaine(i);
                    menu.setLundi(Arrays.asList("Plat " + i, "Dessert " + i));
                    menu.setMardi(Arrays.asList("Plat " + (i+1), "Dessert " + (i+1)));
                    menu.setMercredi(Arrays.asList("Plat " + (i+2), "Dessert " + (i+2)));
                    menu.setJeudi(Arrays.asList("Plat " + (i+3), "Dessert " + (i+3)));
                    menu.setVendredi(Arrays.asList("Plat " + (i+4), "Dessert " + (i+4)));
                    menu.setSamedi(Arrays.asList("Plat " + (i+5), "Dessert " + (i+5)));
                    menu.setDimanche(Arrays.asList("Plat " + (i+6), "Dessert " + (i+6)));
                    iMenuJpaDao.save(menu);
                }

                // Pour PersonneContact
                for (int i = 1; i <= 10; i++) {
                    PersonneContact contact = new PersonneContact();
                    contact.setNom("Nom Contact " + i);
                    contact.setPrenom("Prenom Contact " + i);
                    contact.setEmail("contact" + i + "@exemple.com");
                    contact.setDateNaissance(LocalDate.now().minusYears(25 + i));
                    contact.setTel1("+1234567890");
                    contact.setTel2("+0987654321");
                    contact.setAdresse("Adresse " + i + ", Ville");
                    contact.setChoix(TypePersonne.AMI);  // à remplacer par une valeur appropriée
                    contact.setStatutM("StatutM " + i);
                    iPersonneContactJpaDao.save(contact);
                }

                // Pour PersonneExterne
                for (int i = 1; i <= 10; i++) {
                    PersonneExterne personneExterne = new PersonneExterne();
                    personneExterne.setNom("Nom Externe " + i);
                    personneExterne.setPrenom("Prenom Externe " + i);
                    personneExterne.setEmail("externe" + i + "@exemple.com");
                    personneExterne.setTel("+1234567890");
                    personneExterne.setChoix(TypePersonne.ENFANT); // Remplacez par une valeur appropriée de l'enum
                    iPersonneExterneJpaDao.save(personneExterne);
                }

                // Pour RapportQuotidien
                for (int i = 1; i <= 10; i++) {
                    RapportQuotidien rapport = new RapportQuotidien();
                    rapport.setNumeroR(i);
                    rapport.setFreqCardiaque("Normal");
                    rapport.setFreqRespiratoire("Normal");
                    rapport.setPresArterielle("120/80");
                    rapport.setTemperature("37°C");
                    rapport.setSatOxygene("98%");
                    rapport.setSelle("Normal");
                    rapport.setUrine("Normal");
                    rapport.setSommeil("8 heures");
                    rapport.setCommentaire("Aucun problème détecté pour " + i);
                    iRapportQuotidienJpaDao.save(rapport);
                }

                // Pour RapportVisite
                for (int i = 1; i <= 10; i++) {
                    RapportVisite rapportVisite = new RapportVisite();
                    rapportVisite.setDateVisite(new Date());
                    rapportVisite.setCommentaire("Commentaire visite " + i);
                    iRapportVisiteJpaDao.save(rapportVisite);
                }

                // Pour Resident
                for (int i = 1; i <= 10; i++) {
                    Resident resident = new Resident();
                    resident.setNom("Nom Resident " + i);
                    resident.setPrenom("Prenom Resident " + i);
                    resident.setDateDeNaissance(LocalDate.now().minusYears(25 + i));
                    resident.setEmail("resident" + i + "@exemple.com");
                    resident.setTel("+1234567890");
                    resident.setAdresse("Adresse " + i + ", Ville");
                    resident.setStatus("Status " + i);
                    resident.setDateEntree(new Date());
                    resident.setMotifEntree("Motif Entree " + i);
                    resident.setDateSortie(new Date());
                    resident.setMotifSortie("Motif Sortie " + i);
                    resident.setAntMedical("Aucun");
                    resident.setAntChirugical("Aucun");
                    resident.setNbEnfant(i % 5);
                    resident.setChambre("Chambre " + i);
                    resident.setEtatSante("Bon");
                    resident.setActif(true);
                    iResidentJpaDao.save(resident);
                }
            }*/

        };
    }

}
