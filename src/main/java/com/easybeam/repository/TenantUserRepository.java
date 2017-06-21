package com.easybeam.repository;

import com.easybeam.domain.TenantUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the TenantUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantUserRepository extends JpaRepository<TenantUser,Long> {

    @Query("select tenant_user from TenantUser tenant_user where tenant_user.account.login = ?#{principal.username}")
    List<TenantUser> findByAccountIsCurrentUser();
    
    @Query("select distinct tenant_user from TenantUser tenant_user left join fetch tenant_user.groups left join fetch tenant_user.roles")
    List<TenantUser> findAllWithEagerRelationships();

    @Query("select tenant_user from TenantUser tenant_user left join fetch tenant_user.groups left join fetch tenant_user.roles where tenant_user.id =:id")
    TenantUser findOneWithEagerRelationships(@Param("id") Long id);
    
}
