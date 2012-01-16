package org.rest.web.controller.bar;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.PaginatedResultsRetrievedEvent;
import org.rest.model.Bar;
import org.rest.model.Foo;
import org.rest.persistence.service.IService;
import org.rest.persistence.service.bar.IBarService;
import org.rest.web.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Transactional( propagation = Propagation.REQUIRES_NEW )
public class BarController extends AbstractController< Bar >{
	
	@Autowired
	IBarService service;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	// API
	
	@RequestMapping( value = "admin/bar",params = "page",method = RequestMethod.GET )
	@ResponseBody
	public List< Bar > findPaginated( @RequestParam( "page" ) final int page, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		final Page< Bar > resultPage = this.service.findPaginated( page, 10 );
		
		this.eventPublisher.publishEvent( new PaginatedResultsRetrievedEvent< Foo >( Foo.class, uriBuilder, response, page, resultPage.getTotalPages() ) );
		
		return resultPage.getContent();
	}
	
	@RequestMapping( value = "admin/bar",method = RequestMethod.GET )
	@ResponseBody
	public List< Bar > findAll(){
		return this.findAllInternal();
	}
	
	//
	
	@Override
	protected IService< Bar > getService(){
		return this.service;
	}
	
}
