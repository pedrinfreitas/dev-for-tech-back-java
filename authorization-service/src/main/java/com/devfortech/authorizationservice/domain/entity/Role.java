package com.devfortech.authorizationservice.domain.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Role implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = -612120294562885986L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    public Role(String name) {
        this.description = name;
    }

    @Override
    public String getAuthority(){
        return this.description;
    }

}
