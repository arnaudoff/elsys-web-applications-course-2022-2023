package org.elsys_bg.spring_hw.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
	
}
