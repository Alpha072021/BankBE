/**
 * 
 */
package com.alpha.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.Address;

/**
 * @author Dixith S N
 *
 */
public interface AddressJpaRepository extends JpaRepository<Address, String> {
	@Query("SELECT a.addressId FROM Address a ORDER BY a.addressId DESC LIMIT 1")
	String countOfAdress();

}
