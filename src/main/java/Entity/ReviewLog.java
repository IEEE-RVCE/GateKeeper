package Entity;
import jakarta.persistence.*;
public class ReviewLog {
    @Column(nullable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "formId",referencedColumnName = "id")
    private RequestForm formId;



    @Enumerated(EnumType.STRING)
    private statusEnum status;
    @Column(nullable = true)
    private String comments;
}
