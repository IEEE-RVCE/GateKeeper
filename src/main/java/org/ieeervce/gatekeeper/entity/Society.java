package org.ieeervce.gatekeeper.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity

public class Society {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer societyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private  String email;

    @Column(nullable = false)
    private  boolean isActive;

    @Column(nullable = false)
    private LocalDateTime dateTime;


}
