package org.rina.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.rina.dao.IFichierJpaDao;
import org.rina.model.Fichier;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class FichierServices {

	private IFichierJpaDao fichierdao;

	public FichierServices(IFichierJpaDao fichierdao) {
		this.fichierdao = fichierdao;
	}

	/**
	 * Récupère tous les fichiers associés à une personne de contact, triés par date décroissante.
	 * 
	 * @param persC  L'identifiant de la personne de contact.
	 * @return       Une liste de fichiers associés à la personne de contact, triés par date décroissante.
	 */
	public List<Fichier> findAllByPersonneContactOrderByDateDesc(Long persC) {
		return fichierdao.findAllByPersonneContactOrderByDateDesc(persC);
	}
	
	/**
	 * Recherche un fichier par son identifiant.
	 * 
	 * @param id  L'identifiant du fichier à rechercher.
	 * @return    Le fichier correspondant à l'identifiant, s'il existe.
	 */
	public Optional<Fichier> findById(Long id) {
		return fichierdao.findById(id);
	}

	/**
	 * Vérifie l'existence d'un fichier par son identifiant.
	 * 
	 * @param id  L'identifiant du fichier à vérifier.
	 * @return    true si le fichier existe, sinon false.
	 */
	public boolean existsById(Long id) {
		return fichierdao.existsById(id);
	}

	/**
	 * Récupère tous les fichiers triés par date décroissante.
	 * 
	 * @return  Une liste de fichiers triés par date décroissante.
	 */
	public List<Fichier> findAllOrderByDateDesc() {
		return fichierdao.findAllOrderByDateDesc();
	}

	/**
	 * Ajout d'un nouveau fichier.
	 * 
	 * @param f1  Le fichier à ajouter.
	 * @return    Le fichier ajouté.
	 */
	public Fichier insert(Fichier f1) {
		return fichierdao.save(f1);
	}

	/**
	 * Génère un nom de fichier unique basé sur la date et l'heure actuelles et une extension donnée.
	 * 
	 * @param extension  L'extension du fichier.
	 * @return           Un nom de fichier unique.
	 */
	public String generateUniqueFileName(String extension) {
        String pattern = "yyyyMMddHHmmssSSS";
        String dateToString = new SimpleDateFormat(pattern, new Locale("fr", "FR")).format(new Date());
        return dateToString + extension;
    }

	/**
	 * Obtient l'extension d'un nom de fichier.
	 * 
	 * @param fileName  Le nom du fichier.
	 * @return          L'extension du fichier (y compris le point), ou une chaîne vide si l'extension n'est pas présente.
	 */
    public String getExtension(String fileName){
        int lastIndexOfDot = fileName != null ? fileName.lastIndexOf(".") : -1;
        if (lastIndexOfDot == -1) {
            return "";
        }
        return fileName.substring(lastIndexOfDot);
    }
}
