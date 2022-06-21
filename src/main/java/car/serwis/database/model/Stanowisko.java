package car.serwis.database.model;


import car.serwis.helpers.UsersPermissions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


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

    @Column(
            name = "uprawnienia",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private UsersPermissions uprawnienia;


    @OneToMany(mappedBy = "stanowisko")
    private List<Pracownik> pracownicy = new ArrayList<>();

    @Override
    public String toString() {
        return nazwaStanowiska;
    }
}