package car.serwis.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Samochod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_samochod",
            updatable = false
    )
    private Long idSamochod;

    @Column(
            name = "marka",
            nullable = false
    )
    private String marka;

    @Column(
            name = "model",
            nullable = false
    )
    private String model;

    @OneToMany(mappedBy = "samochod", fetch = FetchType.LAZY)
    private Set<Czesc> czesci = new HashSet<>();

    @OneToMany(mappedBy = "samochod", fetch = FetchType.LAZY)
    private Set<Czesc> zlecenia = new HashSet<>();
}