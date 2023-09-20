package org.rina.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	 * @param personneContact
	 * @return
	 */
	public List<Fichier> findAllByPersonneContactOrderByDateDesc(Long persC) {
		return fichierdao.findAllByPersonneContactOrderByDateDesc(persC);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Optional<Fichier> findById(Long id) {
		return fichierdao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return fichierdao.existsById(id);
	}

	/**
	 * @return
	 */
	public List<Fichier> findAllOrderByDateDesc() {
		return fichierdao.findAllOrderByDateDesc();
	}

	/**
	 * Ajout d'un nouveau Fichier
	 * 
	 * @param c1
	 * @return
	 */
	public Fichier insert(Fichier f1) {
		return fichierdao.save(f1);
	}

	public String generateUniqueFileName(String extension) {
        String pattern = "yyyyMMddHHmmssSSS";
        String dateToString = new SimpleDateFormat(pattern, new Locale("fr", "FR")).format(new Date());
        return dateToString + extension;
    }

    public String getExtension(String fileName){
        int lastIndexOfDot = fileName!=null? fileName.lastIndexOf("."):-1;
        if (lastIndexOfDot == -1) {
            // Es gibt keine Dateierweiterung
            return "";
        }
        return fileName.substring(lastIndexOfDot);
    }
}
