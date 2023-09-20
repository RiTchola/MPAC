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
	 * @param idpc
	 * @return
	 */
	public List<MeetUp> findAllMeetByIdOrderByDateDesc(Long idpc) {
		return meetdao.findAllMeetByIdOrderByDateDesc(idpc);
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
	 * Ajout d'un nouveau MeetUp
	 * 
	 * @param meet1
	 * @return
	 */
	public MeetUp insert(MeetUp meet1) {
		return meetdao.save(meet1);
	}

	/**
	 * @param id
	 * @param meetUp meet1
	 * @see
	 */
	public MeetUp updateMeetUp(Long id, MeetUp meet1) {
		return meetdao.save(meet1);
	}

}
