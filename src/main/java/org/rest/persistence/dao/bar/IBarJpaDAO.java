package org.rest.persistence.dao.bar;

import org.rest.model.Bar;
import org.rest.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBarJpaDAO extends JpaRepository< Bar, Long >{
	
	Foo findByName( final String name );
	
}
