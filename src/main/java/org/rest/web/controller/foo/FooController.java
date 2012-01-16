package org.rest.web.controller.foo;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.PaginatedResultsRetrievedEvent;
import org.rest.common.event.ResourceCreatedEvent;
import org.rest.common.event.SingleResourceRetrievedEvent;
import org.rest.model.Foo;
import org.rest.persistence.service.IService;
import org.rest.persistence.service.foo.IFooService;
import org.rest.web.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Transactional( propagation = Propagation.REQUIRES_NEW )
public class FooController extends AbstractController< Foo >{
	
	@Autowired
	IFooService service;
	
	@Autowired
	ApplicationContext eventPublisher;
	
	// API
	
	@RequestMapping( value = "admin/foo",params = { "page", "size" },method = GET )
	@ResponseBody
	public List< Foo > findPaginated( @RequestParam( "page" ) final int page, @RequestParam( "size" ) final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		final Page< Foo > resultPage = this.service.findPaginated( page, size );
		
		this.eventPublisher.publishEvent( new PaginatedResultsRetrievedEvent< Foo >( Foo.class, uriBuilder, response, page, resultPage.getTotalPages() ) );
		
		return resultPage.getContent();
	}
	
	@RequestMapping( value = "admin/foo",method = GET )
	@ResponseBody
	public List< Foo > findAll(){
		return this.findAllInternal();
	}
	
	@RequestMapping( value = "admin/foo/{id}",method = GET )
	@ResponseBody
	public Foo findOne( @PathVariable( "id" ) final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		final Foo resourceById = this.findOneInternal( id );
		
		this.eventPublisher.publishEvent( new SingleResourceRetrievedEvent< Foo >( Foo.class, uriBuilder, response ) );
		return resourceById;
	}
	
	@RequestMapping( value = "admin/foo",method = POST )
	@ResponseStatus( HttpStatus.CREATED )
	public void create( @RequestBody final Foo resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		this.saveInternal( resource );
		
		this.eventPublisher.publishEvent( new ResourceCreatedEvent< Foo >( Foo.class, uriBuilder, response, resource.getId() ) );
	}
	
	@RequestMapping( value = "admin/foo",method = PUT )
	@ResponseStatus( HttpStatus.OK )
	public void update( @RequestBody final Foo resource ){
		this.updateInternal( resource );
	}
	
	@RequestMapping( value = "admin/foo/{id}",method = DELETE )
	@ResponseStatus( HttpStatus.NO_CONTENT )
	public void delete( @PathVariable( "id" ) final Long id ){
		this.deleteByIdInternal( id );
	}
	
	//
	
	@Override
	protected final IService< Foo > getService(){
		return this.service;
	}
	
}
