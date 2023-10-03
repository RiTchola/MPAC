package org.rina.service;

import org.rina.dao.ICodeQrJpaDao;
import org.rina.model.CodeQr;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CodeQrServices {

	private ICodeQrJpaDao codedao;

	public CodeQrServices(ICodeQrJpaDao codedao) {
		this.codedao = codedao;
	}

	/**
	 * Récupère le nombre total d'identifiants (IDs) à partir d'une source externe.
	 *
	 * @return Le nombre total d'identifiants.
	 */
	public int getAllCountId() {
		return codedao.getAllCountId();
	}

	/**
	 * Recherche un code unique dans la base de données
	 * 
	 * @param codeUnique Le code unique à rechercher.
	 * @return La valeur associé au code unique s'il est trouvé, sinon null.
	 */
	public Long findByCodeUnique(Long codeUnique) {
	    return codedao.findByCodeUnique(codeUnique);
	}

	/**
	 * Ajout d'un nouveau codeqr.
	 * 
	 * @param rapvi  Le codeqr à ajouter.
	 * @return       Le codeqr ajouté.
	 */
	public CodeQr insert(CodeQr code) {
		return codedao.save(code);
	}

}
