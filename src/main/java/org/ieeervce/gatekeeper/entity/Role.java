package org.ieeervce.gatekeeper.entity;
import jakarta.persistence.*;

@Entity
public class Role {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(unique = true,nullable = false)
    private int value;

    @Column(unique = true,nullable = false)
    private String roleName;

}
