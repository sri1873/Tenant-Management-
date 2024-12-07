package com.tenant.management.user.repositories;

import com.tenant.management.user.entities.RTB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RtbRepository extends JpaRepository<RTB, Long> {

}
