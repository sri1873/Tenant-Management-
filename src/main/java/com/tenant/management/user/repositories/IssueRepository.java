package com.tenant.management.user.repositories;

import com.tenant.management.user.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueRepository extends JpaRepository<Issue, UUID> {
    @Query(value = "SELECT * FROM issue where issue.issueId =?1", nativeQuery = true)
    Optional<Issue> findByUuid(UUID issueId);

}
