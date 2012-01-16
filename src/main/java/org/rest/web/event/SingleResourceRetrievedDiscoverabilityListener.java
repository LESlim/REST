package org.rest.web.event;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.SingleResourceRetrievedEvent;
import org.rest.common.util.HttpConstants;
import org.rest.common.util.RESTURIUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

@SuppressWarnings( "rawtypes" )
@Component
final class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener< SingleResourceRetrievedEvent >{
	
	@Override
	public final void onApplicationEvent( final SingleResourceRetrievedEvent ev ){
		Preconditions.checkNotNull( ev );
		
		this.addLinkHeaderOnSingleResourceRetrieval( ev.getUriBuilder(), ev.getResponse(), ev.getClazz() );
	}
	
	final void addLinkHeaderOnSingleResourceRetrieval( final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz ){
		final String resourceName = clazz.getSimpleName().toString().toLowerCase();
		final String uriForResourceCreation = uriBuilder.path( "/admin/" + resourceName ).build().encode().toUriString();
		
		final String linkHeaderValue = RESTURIUtil.createLinkHeader( uriForResourceCreation, RESTURIUtil.REL_COLLECTION );
		response.addHeader( HttpConstants.LINK_HEADER, linkHeaderValue );
	}
}
