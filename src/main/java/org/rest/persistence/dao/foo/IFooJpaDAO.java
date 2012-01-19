package org.rest.persistence.dao.foo;

import org.rest.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @see <a href="http://static.springsource.org/spring-data/data-jpa/docs/1.0.2.RELEASE/reference/html">spring-data-jpa</a>
 */
public interface IFooJpaDAO extends JpaRepository< Foo, Long >{
	
	Foo findByName( final String name );
	
}
