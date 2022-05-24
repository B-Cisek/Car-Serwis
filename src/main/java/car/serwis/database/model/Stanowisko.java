package car.serwis.database.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stanowisko {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_stanowisko",
            updatable = false
    )
    private Long idStanowisko;

    @Column(
            name = "nazwa_stanowiska",
            nullable = false
    )
    private String nazwaStanowiska;

    @OneToMany(mappedBy = "stanowisko", fetch = FetchType.LAZY)
    private Set<Pracownik> pracownicy = new HashSet<>();

}