package org.sid.saranApp.repository;

import org.sid.saranApp.model.CustomerBoutique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerBoutique,String> {
}
