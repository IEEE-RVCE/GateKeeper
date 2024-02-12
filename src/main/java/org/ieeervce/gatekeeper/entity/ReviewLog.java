package org.ieeervce.gatekeeper.entity;

import jakarta.persistence.*;

@Entity
public class ReviewLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "formId",referencedColumnName = "id")
    private RequestForm formId;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(nullable = true)
    private String comments;
}
