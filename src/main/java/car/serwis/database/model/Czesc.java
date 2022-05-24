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
public class Czesc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_czesc",
            updatable = false
    )
    private Long idCzesc;

    @Column(
            name = "nazwa_czesci",
            nullable = false
    )
    private String nazwaCzesci;

    @Column(
            name = "opis_czesc",
            nullable = false
    )
    private String opisCzesc;

    @Column(
            name = "ilosc",
            nullable = false
    )
    private Double ilosc;

    @Column(
            name = "producent",
            nullable = false
    )
    private String producent;

    @ManyToOne
    @JoinColumn(name = "id_kategoria", referencedColumnName = "id_kategoria")
    private Kategoria kategoria;

    @ManyToOne
    @JoinColumn(name = "id_jednostka", referencedColumnName = "id_jednostka")
    private Jednostka jednostka;

    @ManyToOne
    @JoinColumn(name = "id_samochod", referencedColumnName = "id_samochod")
    private Samochod samochod;

    @ManyToMany(mappedBy = "czesci")
    private Set<Zlecenie> zlecenia = new HashSet<>();
    }

