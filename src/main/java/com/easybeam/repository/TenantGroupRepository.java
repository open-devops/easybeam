package com.easybeam.repository;

import com.easybeam.domain.TenantGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the TenantGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantGroupRepository extends JpaRepository<TenantGroup,Long> {

    @Query("select tenant_group from TenantGroup tenant_group where tenant_group.account.login = ?#{principal.username}")
    List<TenantGroup> findByAccountIsCurrentUser();
    
    @Query("select distinct tenant_group from TenantGroup tenant_group left join fetch tenant_group.roles")
    List<TenantGroup> findAllWithEagerRelationships();

    @Query("select tenant_group from TenantGroup tenant_group left join fetch tenant_group.roles where tenant_group.id =:id")
    TenantGroup findOneWithEagerRelationships(@Param("id") Long id);
    
}
