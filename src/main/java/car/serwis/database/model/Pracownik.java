package car.serwis.database.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pracownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_pracownik",
            updatable = false
    )
    private Long idPracownik;

    @Column(
            name = "imie",
            nullable = false
    )
    private String imie;

    @Column(
            name = "nazwisko",
            nullable = false
    )
    private String nazwisko;

    @Column(
            name = "pracuje_od",
            nullable = false
    )
    private LocalDate pracujeOd;

    @Column(
            name = "login",
            nullable = false
    )
    private String login;

    @Column(
            name = "haslo",
            nullable = false
    )
    private String haslo;

    @ManyToOne
    @JoinColumn(name = "id_stanowisko", referencedColumnName = "id_stanowisko")
    private Stanowisko stanowisko;

    @OneToMany(mappedBy = "pracownik", fetch = FetchType.LAZY)
    private Set<Zlecenie> zlecenia = new HashSet<>();

}
