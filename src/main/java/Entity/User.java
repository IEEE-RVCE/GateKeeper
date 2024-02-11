package Entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "roleName")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "society", referencedColumnName = "name",nullable = true)
    private Society society;


    @Column(nullable = false, unique = true)
    private  long number;

    @Column(nullable = false)
    private LocalDateTime dateTime;


    @Column(nullable = false)
    private  boolean enabled;


}
