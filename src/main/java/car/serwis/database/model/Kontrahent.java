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
    private Integer telefon;

    @Column
    private String nazwaFirmy;

    @Column
    private Integer nip;

    @Column
    private Integer pesel;

    @Column
    private String kodPocztowy;

    @Column
    private String miejscowosc;

    @Column
    private String ulica;

    @OneToMany
    private List<Zlecenie> zlecenia;









}
