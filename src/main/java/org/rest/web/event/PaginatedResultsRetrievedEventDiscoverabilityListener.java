package org.rest.web.event;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.PaginatedResultsRetrievedEvent;
import org.rest.common.util.HttpConstants;
import org.rest.common.util.RESTURIUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

@SuppressWarnings( "rawtypes" )
@Component
final class PaginatedResultsRetrievedEventDiscoverabilityListener implements ApplicationListener< PaginatedResultsRetrievedEvent >{
	
	// API
	
	@Override
	public final void onApplicationEvent( final PaginatedResultsRetrievedEvent ev ){
		Preconditions.checkNotNull( ev );
		
		this.addLinkHeaderOnPagedResourceRetrieval( ev.getUriBuilder(), ev.getResponse(), ev.getClazz(), ev.getPage(), ev.getTotalPages() );
	}
	
	//
	
	final void addLinkHeaderOnPagedResourceRetrieval( final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz, final int page, final int totalPages ){
		final String resourceName = clazz.getSimpleName().toString().toLowerCase();
		uriBuilder.path( "/admin/" + resourceName );
		
		final StringBuilder linkHeader = new StringBuilder();
		if( this.hasNextPage( page, totalPages ) ){
			final String uriForNextPage = this.constructNextPageUri( uriBuilder, page );
			linkHeader.append( RESTURIUtil.createLinkHeader( uriForNextPage, RESTURIUtil.REL_NEXT ) );
		}
		if( this.hasPreviousPage( page ) ){
			final String uriForPrevPage = this.constructPrevPageUri( uriBuilder, page );
			if( linkHeader.length() > 0 ){
				linkHeader.append( ", " );
			}
			linkHeader.append( RESTURIUtil.createLinkHeader( uriForPrevPage, RESTURIUtil.REL_PREV ) );
		}
		if( this.hasLastPage( page, totalPages ) ){
			final String uriForLastPage = this.constructLastPageUri( uriBuilder, totalPages );
			if( linkHeader.length() > 0 ){
				linkHeader.append( ", " );
			}
			linkHeader.append( RESTURIUtil.createLinkHeader( uriForLastPage, RESTURIUtil.REL_LAST ) );
		}
		
		response.addHeader( HttpConstants.LINK_HEADER, linkHeader.toString() );
	}
	
	final String constructNextPageUri( final UriComponentsBuilder uriBuilder, final int page ){
		return uriBuilder.queryParam( "page", page + 1 ).queryParam( "size", 10 ).build().encode().toUriString();
	}
	final String constructPrevPageUri( final UriComponentsBuilder uriBuilder, final int page ){
		return uriBuilder.replaceQueryParam( "page", page - 1 ).replaceQueryParam( "size", 10 ).build().encode().toUriString();
	}
	final String constructLastPageUri( final UriComponentsBuilder uriBuilder, final int totalPages ){
		return uriBuilder.replaceQueryParam( "page", totalPages ).replaceQueryParam( "size", 10 ).build().encode().toUriString();
	}
	
	private boolean hasLastPage( final int page, final int totalPages ){
		return totalPages > 1 && this.hasNextPage( page, totalPages );
	}
	private boolean hasPreviousPage( final int page ){
		return page > 0;
	}
	private boolean hasNextPage( final int page, final int totalPages ){
		return page < totalPages - 1;
	}
	
}
