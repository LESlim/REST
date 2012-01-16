package org.rest.persistence.dao.foo;

import org.rest.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFooJpaDAO extends JpaRepository< Foo, Long >{
	
	Foo findByName( final String name );
	
}
