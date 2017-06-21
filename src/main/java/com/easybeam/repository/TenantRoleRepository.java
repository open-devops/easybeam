package com.easybeam.repository;

import com.easybeam.domain.TenantRole;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the TenantRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantRoleRepository extends JpaRepository<TenantRole,Long> {
    
    @Query("select distinct tenant_role from TenantRole tenant_role left join fetch tenant_role.policies")
    List<TenantRole> findAllWithEagerRelationships();

    @Query("select tenant_role from TenantRole tenant_role left join fetch tenant_role.policies where tenant_role.id =:id")
    TenantRole findOneWithEagerRelationships(@Param("id") Long id);
    
}
