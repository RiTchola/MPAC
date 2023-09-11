package org.rina.service;

import java.util.List;

import java.util.Optional;

import org.rina.dao.IMeetUpJpaDao;
import org.rina.model.MeetUp;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MeetUpServices {
	
	private IMeetUpJpaDao meetdao;

	/**
	 * @param meetdao
	 */
	public MeetUpServices(IMeetUpJpaDao meetdao) {
		this.meetdao = meetdao;
	}

	/**
	 * @return
	 */
	public List<MeetUp> findAllMeetOrderByDateDesc() {
		return meetdao.findAllMeetOrderByDateDesc();
	}

	/**
	 * @return
	 */
	public List<MeetUp> findAll() {
		return meetdao.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<MeetUp> findById(Long id) {
		return meetdao.findById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean existsById(Long id) {
		return meetdao.existsById(id);
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		meetdao.deleteById(id);
	}
	
	/**
	 * Ajout d'un nouveau MeetUp
	 * 
	 * @param meet1
	 * @return
	 */
	public MeetUp insert(MeetUp meet1) {
		return update(meet1);
	}

	public MeetUp update(MeetUp meet1) {
		assert meet1 != null : "Le meetUp doit exister";
		return meetdao.save(meet1);
	}	


}
