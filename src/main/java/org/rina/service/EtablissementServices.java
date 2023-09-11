package org.rina.service;

import java.util.List;
import java.util.Optional;

import org.rina.dao.IEtablissementJpaDao;
import org.rina.model.Etablissement;
import org.rina.model.Resident;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class EtablissementServices {
	
	private IEtablissementJpaDao etablissementdao;
		
		public EtablissementServices(IEtablissementJpaDao etablissementdao) {
			this.etablissementdao = etablissementdao;
		}

		/**
		 * @param etablissement
		 * @return
		 */
		public List<Resident> findAllResid() {
			return etablissementdao.findAllResid();
		}

		/**
		 * @param etablissement
		 * @return
		 */
		public int countAllResid() {
			return etablissementdao.countAllResid();
		}

		/**
		 * @param nom
		 * @param adresse
		 * @return
		 */
		public Optional<Etablissement> findByNameAndAdresse(String nom, String adresse) {
			return etablissementdao.findByNameAndAdresse(nom, adresse);
		}

		/**
		 * @param username
		 * @return
		 */
		public Optional<Etablissement> findByUsername(String username) {
			return etablissementdao.findByUsername(username);
		}

		/**
		 * @return
		 */
		public List<Etablissement> findAll() {
			return etablissementdao.findAll();
		}

		/**
		 * @param username
		 * @return
		 */
		public boolean existEtabByUserName(String username) {
			return etablissementdao.existEtabByUserName(username);
		}

		/**
		 * @param id
		 * @return
		 */
		public Optional<Etablissement> findById(Long id) {
			return etablissementdao.findById(id);
		}

		/**
		 * @param id
		 * @return
		 */
		public boolean existsById(Long id) {
			return etablissementdao.existsById(id);
		}

		/**
		 * @param id
		 */
		public void deleteById(Long id) {
			etablissementdao.deleteById(id);
		}
		
		/**
		 * Ajout d'un nouveau Etablissement
		 * 
		 * @param c1
		 * @return
		 */
		public Etablissement insert(Etablissement et1) {
			return update(et1);
		}

		public Etablissement update(Etablissement et1) {
			assert et1 != null : "L'etablissement doit exister";
			return etablissementdao.save(et1);
		}

}
