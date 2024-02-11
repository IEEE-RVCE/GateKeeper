package Entity;
import jakarta.persistence.*;
public class Role {

    @Column(unique = true,nullable = false)
    private int id;

    @Column(unique = true,nullable = false)
    private String roleName;

}
