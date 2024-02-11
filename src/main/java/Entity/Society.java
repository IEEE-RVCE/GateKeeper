package Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Society {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private  String email;

    @Column(nullable = false)
    private  boolean isActive;

    @Column(nullable = false)
    private LocalDateTime dateTime;


}
