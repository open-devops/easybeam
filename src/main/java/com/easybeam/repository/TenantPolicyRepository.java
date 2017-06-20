package com.easybeam.repository;

import com.easybeam.domain.TenantPolicy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the TenantPolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantPolicyRepository extends JpaRepository<TenantPolicy,Long> {

    @Query("select distinct tenant_policy from TenantPolicy tenant_policy left join fetch tenant_policy.permissions")
    List<TenantPolicy> findAllWithEagerRelationships();

    @Query("select tenant_policy from TenantPolicy tenant_policy left join fetch tenant_policy.permissions where tenant_policy.id =:id")
    TenantPolicy findOneWithEagerRelationships(@Param("id") Long id);

}
