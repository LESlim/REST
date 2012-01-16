package org.rest.web.common;

import java.util.List;

import org.rest.common.IEntity;
import org.rest.common.util.RestPreconditions;
import org.rest.exceptions.BadRequestException;
import org.rest.exceptions.ResourceNotFoundException;
import org.rest.persistence.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;

public abstract class AbstractController< T extends IEntity >{
	protected final Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	protected abstract IService< T > getService();
	
	// get/find
	
	protected final T findOneInternal( final Long id ){
		T resource = null;
		try{
			resource = RestPreconditions.checkNotNull( this.getService().findOne( id ) );
		}
		catch( final InvalidDataAccessApiUsageException ex ){
			this.logger.error( "", ex );
			throw new BadRequestException( ex );
		}
		
		return resource;
	}
	
	protected final List< T > findAllInternal(){
		return RestPreconditions.checkNotNull( this.getService().findAll() );
	}
	
	// create/persist
	
	protected final void saveInternal( final T resource ){
		RestPreconditions.checkRequestElementNotNull( resource );
		RestPreconditions.checkRequestState( resource.getId() == null );
		try{
			this.getService().save( resource );
		}
		catch( final IllegalStateException ex ){
			this.logger.error( "", ex );
			throw new ResourceNotFoundException( ex );
		}
		catch( final IllegalArgumentException ex ){
			this.logger.error( "", ex );
			throw new BadRequestException( ex );
		}
	}
	
	// update
	
	protected final void updateInternal( final T resource ){
		RestPreconditions.checkRequestElementNotNull( resource );
		RestPreconditions.checkRequestElementNotNull( resource.getId() );
		RestPreconditions.checkNotNull( this.getService().findOne( resource.getId() ) );
		
		try{
			this.getService().save( resource );
		}
		catch( final InvalidDataAccessApiUsageException ex ){
			this.logger.error( "", ex );
			throw new BadRequestException( ex );
		}
	}
	
	// delete/remove
	
	protected final void deleteByIdInternal( final long id ){
		try{
			this.getService().delete( id );
		}
		catch( final InvalidDataAccessApiUsageException ex ){
			this.logger.error( "", ex );
			throw new ResourceNotFoundException( ex );
		}
	}
	
}
