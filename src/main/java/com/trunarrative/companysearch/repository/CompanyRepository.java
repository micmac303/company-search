package com.trunarrative.companysearch.repository;

import com.trunarrative.companysearch.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByCompanyNumber(String companyNumber);
}
