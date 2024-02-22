package com.alpha.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.bankApp.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

}
