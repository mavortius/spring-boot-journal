package com.apress.journal.domain;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleAuthority authority = RoleAuthority.READER;

    public Role() {
    }

    public Role(RoleAuthority authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(RoleAuthority authority) {
        this.authority = authority;
    }
}
