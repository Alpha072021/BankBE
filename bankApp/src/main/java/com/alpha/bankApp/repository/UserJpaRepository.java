/**
 * 
 */
package com.alpha.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.enums.DocumentType;

/**
 * @author Dixith S N
 *
 */

public interface UserJpaRepository extends JpaRepository<User, String> {

	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

	@Query("SELECT u.userId FROM bank_user u ORDER BY u.createdDateAndTime DESC LIMIT 1")
	String findLastUserId();

	@Query("SELECT p.documentImagePath FROM bank_user e JOIN e.docsContainer d JOIN d.documents p WHERE e.userId=?1 AND p.type=?2")
	String findUserProfileById(String id, DocumentType documentType);

}
