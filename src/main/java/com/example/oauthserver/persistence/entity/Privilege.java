package com.example.oauthserver.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Project title: oauth-server
 *
 * @author johnadeshola
 * Date: 3/28/23
 * Time: 11:06 AM
 */
@Entity
@Table(name = "privileges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class Privilege extends BaseEntity implements GrantedAuthority {

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", length = 120)
    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}
