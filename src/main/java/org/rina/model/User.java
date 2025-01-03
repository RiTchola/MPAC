package org.rina.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.rina.enums.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "TUSER", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@Entity
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Email 
    @Column(nullable = false) 
    private String username;

    private String password;

    private Roles role;

    @Column(nullable = false) 
    @Builder.Default // Utilise le constructeur par défaut de Lombok pour initialiser la valeur par défaut à true
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}