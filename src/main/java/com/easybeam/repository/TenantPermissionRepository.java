package com.easybeam.repository;

import com.easybeam.domain.TenantPermission;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TenantPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantPermissionRepository extends JpaRepository<TenantPermission,Long> {
    
}
