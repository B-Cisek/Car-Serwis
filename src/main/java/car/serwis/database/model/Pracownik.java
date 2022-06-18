package car.serwis.database.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
            nullable = false,
            unique = true
    )
    private String login;

    @Column(
            name = "haslo",
            nullable = false
    )
    private String haslo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_stanowisko", referencedColumnName = "id_stanowisko")
    private Stanowisko stanowisko;


    @OneToMany(mappedBy = "pracownik")
    private List<Zlecenie> zlecenia = new ArrayList<>();

    public Pracownik(Long idPracownik, String imie, String nazwisko, LocalDate pracujeOd, String login, String haslo) {
        this.idPracownik = idPracownik;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pracujeOd = pracujeOd;
        this.login = login;
        this.haslo = haslo;
    }

    @Override
    public String toString() {
        return imie + " " + nazwisko;
    }
}
