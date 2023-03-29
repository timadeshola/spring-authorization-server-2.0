package com.example.oauthserver.persistence.repository;

import com.example.oauthserver.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Project title: oauth-server
 *
 * @author johnadeshola
 * Date: 3/28/23
 * Time: 11:13 AM
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
