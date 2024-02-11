package org.ieeervce.gatekeeper.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity

public class RequestForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String eventTitle;

    @ManyToOne
    @JoinColumn(name = "requester",referencedColumnName = "userId")
    private User requester;

    @Column(nullable = false)
    private LocalDateTime dateTime;



    @Enumerated(EnumType.STRING)
    private FinalStatus status;
    @Lob @Basic(fetch = FetchType.EAGER)
    @Column( nullable = false,columnDefinition = "LONGBLOB")
    private byte[] formPDF;

    @OneToMany(mappedBy = "formId", cascade = CascadeType.ALL)
    private List<ReviewLog> reviewLogs;


}
