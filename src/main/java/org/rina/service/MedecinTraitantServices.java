package org.rina.service;

import java.util.List;


import java.util.Optional;

import org.rina.dao.IMedecinTraitantJpaDao;
import org.rina.model.MedecinTraitant;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MedecinTraitantServices {
	
	private IMedecinTraitantJpaDao medecinTraitantdao;
		
		public MedecinTraitantServices(IMedecinTraitantJpaDao medecinTraitantdao) {
			this.medecinTraitantdao = medecinTraitantdao;
		}

		/**
		 * @param idResid
		 * @return
		 */
		public Optional<MedecinTraitant> findResidByMedecin(Long idResid) {
			return medecinTraitantdao.findMedecinByResid(idResid);
		}


		/**
		 * @return
		 */
		public List<MedecinTraitant> findAll() {
			return medecinTraitantdao.findAll();
		}

		/**
		 * @param id
		 * @return
		 */
		public Optional<MedecinTraitant> findById(Long id) {
			return medecinTraitantdao.findById(id);
		}

		/**
		 * @param id
		 * @return
		 */
		public boolean existsById(Long id) {
			return medecinTraitantdao.existsById(id);
		}

	
		/**
		 * Ajout d'un nouveau MedecinTraitant
		 * 
		 * @param c1
		 * @return
		 */
		public MedecinTraitant insert(MedecinTraitant m1) {
			return update(m1);
		}

		public MedecinTraitant update(MedecinTraitant m1) {
			assert m1 != null : "Le medecinTraitant doit exister";
			return medecinTraitantdao.save(m1);
		}	
		
}
