package car.serwis.database.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Czesc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_czesc")
    private Long idCzesc;

    @Column
    private String nazwa;

    @Column
    private String producent;

    @Column
    private String opis;

    @Column
    private Double ilosc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idJednostka")
    private Jednostka jednostka;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idKategoria")
    private  Kategoria kategoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idSamochod")
    private  Samochod samochod;

    @ManyToMany(mappedBy = "czesci")
    private Set<Zlecenie> zlecenia = new HashSet<>();


}
