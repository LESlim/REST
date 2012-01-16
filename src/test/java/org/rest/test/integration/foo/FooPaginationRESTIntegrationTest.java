package org.rest.test.integration.foo;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.rest.test.integration.test.AbstractRESTIntegrationTest;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.SecurityComponent;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class FooPaginationRESTIntegrationTest extends AbstractRESTIntegrationTest{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	SecurityComponent securityComponent;
	
	// tests
	
	// GET (paged)
	
	@Test
	public final void whenResourcesAreRetrievedPaged_thenNoExceptions(){
		this.givenAuthenticated().get( this.paths.getFooURL() + "?page=1&size=10" );
	}
	@Test
	public final void whenResourcesAreRetrievedPaged_then200IsReceived(){
		// When
		final Response response = this.givenAuthenticated().get( this.paths.getFooURL() + "?page=1&size=10" );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned(){
		// When
		final Response response = this.givenAuthenticated().get( this.paths.getFooURL() + "?page=1&size=10" );
		
		// Then
		assertFalse( response.body().as( List.class ).isEmpty() );
	}
	@Test
	public final void whenPageOfResourcesAreRetrievedOutOfBounds_thenResourcesPageIsReturnedEmpty(){
		// When
		final Response response = this.givenAuthenticated().get( this.paths.getFooURL() + "?page=" + randomNumeric( 5 ) + "&size=10" );
		
		// Then
		assertTrue( response.body().as( List.class ).isEmpty() );
	}
	
	// util
	
	private final RequestSpecification givenAuthenticated(){
		return this.securityComponent.givenAuthenticatedByBasicAuth();
	}
	
}
