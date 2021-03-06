package car.serwis.database.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jednostka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_jednostka",
            updatable = false
    )
    private Long idJednostka;

    @Column(
            name = "nazwa_jednostki",
            nullable = false
    )
    private String nazwaJednostki;

    @Column(
            name = "skrot",
            nullable = false
    )
    private String skrot;

    @OneToMany(mappedBy = "jednostka", fetch = FetchType.LAZY)
    private Set<Czesc> czesci = new HashSet<>();

    @Override
    public String toString() {
        return skrot;
    }

}