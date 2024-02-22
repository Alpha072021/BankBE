package com.alpha.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.bankApp.entity.Statement;

public interface StatementRepository extends JpaRepository<Statement, Long> {

}
