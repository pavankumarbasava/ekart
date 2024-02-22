package com.ekart.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.userservice.entity.Address;
import com.ekart.userservice.entity.PaymentInformation;



public interface PaymentRepository extends JpaRepository<PaymentInformation, Long>{

//	@Modifying
//	@Query("update User u set u.isActivated=:flag  where u.username=:name")
//
//	public void updateActivationFlag(@Param("flag") Boolean flag, @Param("name") String name);
//	
	@Query("select p from PaymentInformation p  , User u  where p.user=u.id and u.username=:name")
	public Optional<List<PaymentInformation>> getAllPayments(@Param("name") String name);
//	
//	@Query("select *from address where u.username=:name")
//	public Optional<List<Address>> getAllAddresses(@Param("name") String name);
	
	
	
}
