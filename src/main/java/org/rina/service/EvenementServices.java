package org.rina.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.rina.dao.IEvenementJpaDao;
import org.rina.model.Evenement;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class EvenementServices {
	
	private IEvenementJpaDao evenementdao;
		
		public EvenementServices(IEvenementJpaDao evenementdao) {
			this.evenementdao = evenementdao;
		}

		/**
		 * @param dateEvent
		 * @return
		 */
		public List<Evenement> findEventByDate(Date dateEvent) {
			return evenementdao.findEventByDate(dateEvent);
		}

		/**
		 * @param nom
		 * @param date
		 * @return
		 */
		public List<Evenement> findEventByName(String nom, Date date) {
			return evenementdao.findEventByName(nom, date);
		}

		/**
		 * @return
		 */
		public List<Evenement> findAll() {
			return evenementdao.findAll();
		}

		/**
		 * @param id
		 * @return
		 */
		public Optional<Evenement> findById(Long id) {
			return evenementdao.findById(id);
		}

		/**
		 * @param id
		 * @return
		 */
		public boolean existsById(Long id) {
			return evenementdao.existsById(id);
		}

		/**
		 * @param id
		 */
		public void deleteById(Long id) {
			evenementdao.deleteById(id);
		}
		
		/**
		 * Ajout d'un nouveau evenement
		 * 
		 * @param c1
		 * @return
		 */
		public Evenement insert(Evenement e1) {
			return update(e1);
		}

		public Evenement update(Evenement e1) {
			assert e1 != null : "Le evenement doit exister";
			return evenementdao.save(e1);
		}
		
}
