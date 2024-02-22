package com.ekart.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ekart.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String name);

	Optional<User> findByEmail(String name);

	Optional<User> findById(Long id);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User getUserByUsername(String username);
	
	//@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	@Modifying
	@Query("update User u set u.isActivated=:flag  where u.username=:name")

	public void updateActivationFlag(@Param("flag") Boolean flag, @Param("name") String name);
	

	List<User> findAll();

//	@Modifying
//	@Query("update User u set u.isActivated=:flag  where u.username=:name")
//	void updateBlockedFlags(boolean flag, String fieldName);
}
