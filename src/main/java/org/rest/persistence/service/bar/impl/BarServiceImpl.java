package org.rest.persistence.service.bar.impl;

import org.rest.model.Bar;
import org.rest.persistence.dao.bar.IBarJpaDAO;
import org.rest.persistence.service.bar.IBarService;
import org.rest.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional( propagation = Propagation.MANDATORY )
public class BarServiceImpl extends AbstractService< Bar > implements IBarService{
	
	@Autowired
	IBarJpaDAO dao;
	
	// API
	
	@Override
	public Page< Bar > findPaginated( final int page, final int size ){
		return this.dao.findAll( new PageRequest( page, size ) );
	}
	
	// Spring
	
	@Override
	protected final IBarJpaDAO getDao(){
		return this.dao;
	}
	
}
