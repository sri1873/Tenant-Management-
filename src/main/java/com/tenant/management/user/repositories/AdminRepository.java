package com.tenant.management.user.repositories;

import com.tenant.management.user.entities.Admin;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    @Query(value = "SELECT * FROM admin where admin.adminId =?1", nativeQuery = true)
    Optional<Admin> findByUuid(UUID adminId);

    @Query(value = "DELETE * FROM admin where admin.adminId=?1", nativeQuery = true)
    void deleteById(@NotNull UUID adminId);
}
