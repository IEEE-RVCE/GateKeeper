package org.ieeervce.gatekeeper.Entity;
import jakarta.persistence.*;

@Entity

public class ReviewLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "formId",referencedColumnName = "id")
    private RequestForm formId;



    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(nullable = true)
    private String comments;
}
