package org.rest.persistence.service.foo.impl;

import org.rest.model.Foo;
import org.rest.persistence.dao.foo.IFooJpaDAO;
import org.rest.persistence.service.common.AbstractService;
import org.rest.persistence.service.foo.IFooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional( propagation = Propagation.MANDATORY )
public class FooServiceImpl extends AbstractService< Foo > implements IFooService{
	
	@Autowired
	IFooJpaDAO dao;
	
	// API
	
	// get/find
	
	@Override
	@Transactional( readOnly = true )
	public Page< Foo > findPaginated( final int page, final int size ){
		return this.dao.findAll( new PageRequest( page, size ) );
	}
	
	// Spring
	
	@Override
	protected final IFooJpaDAO getDao(){
		return this.dao;
	}
	
}
