package org.rina.dao;

import org.rina.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuJpaDao extends JpaRepository<Menu, Long>{




}
