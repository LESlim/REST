package org.rest.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rest.persistence.dao.foo.FooDAOPersistenceIntegrationTest;
import org.rest.persistence.service.foo.FooServicePersistenceIntegrationTest;

@RunWith( Suite.class )
@SuiteClasses( { FooServicePersistenceIntegrationTest.class, FooDAOPersistenceIntegrationTest.class } )
public final class IntegrationPersistenceTestSuite{
	//
}
