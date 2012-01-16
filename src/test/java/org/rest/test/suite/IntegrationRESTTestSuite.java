package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.security.SecurityRESTIntegrationTest;
import org.rest.test.integration.foo.FooDiscoverabilityRESTIntegrationTest;
import org.rest.test.integration.foo.FooMimeRESTIntegrationTest;
import org.rest.test.integration.foo.FooRESTIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { SecurityRESTIntegrationTest.class, FooDiscoverabilityRESTIntegrationTest.class, FooRESTIntegrationTest.class, FooMimeRESTIntegrationTest.class } )
public final class IntegrationRESTTestSuite{
	//
}
