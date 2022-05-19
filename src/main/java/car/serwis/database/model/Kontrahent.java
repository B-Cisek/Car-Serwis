package car.serwis.database.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kontrahent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kontrahent")
    private Long idKontrahent;

    @Column
    private String imie;

    @Column
    private String nazwisko;

    @Column
    private String telefon;

    @Column
    private String nazwaFirmy;

    @Column
    private String nip;

    @Column
    private String pesel;

    @Column
    private String kodPocztowy;

    @Column
    private String miejscowosc;

    @Column
    private String ulica;

    @OneToMany
    private List<Zlecenie> zlecenia;

    public Kontrahent(String imie, String nazwisko, String telefon, String nazwaFirmy, String nip, String pesel, String kodPocztowy, String miejscowosc, String ulica) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefon = telefon;
        this.nazwaFirmy = nazwaFirmy;
        this.nip = nip;
        this.pesel = pesel;
        this.kodPocztowy = kodPocztowy;
        this.miejscowosc = miejscowosc;
        this.ulica = ulica;
    }
}
