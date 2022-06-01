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
public class Kontrahent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_kontrahent",
            updatable = false
    )
    private Long idKontrahent;

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
            name = "nazwa_firmy",
            nullable = false
    )
    private String nazwaFirmy;

    @Column(
            name = "nip",
            nullable = false
    )
    private String nip;

    @Column(
            name = "pesel",
            nullable = false
    )
    private String pesel;

    @Column(
            name = "telefon",
            nullable = false
    )
    private String telefon;

    @Column(
            name = "kod_pocztowy",
            nullable = false
    )
    private String kodPocztowy;

    @Column(
            name = "miejscowosc",
            nullable = false
    )
    private String miejscowosc;

    @Column(
            name = "ulica",
            nullable = false
    )
    private String ulica;

    @OneToMany(mappedBy = "kontrahent", fetch = FetchType.LAZY)
    private Set<Zlecenie> zlecenia = new HashSet<>();

    @Override
    public String toString() {
        return nazwaFirmy;
    }
}