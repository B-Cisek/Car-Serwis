package car.serwis.database.model;



import car.serwis.helpers.ZlecenieStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zlecenie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zlecenie")
    private Long idZlecenie;

    @Column(name = "data_przyjecia")
    private LocalDate dataPrzyjecia;

    @Column
    private String opis;

    @Enumerated(EnumType.STRING)
    private ZlecenieStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idSamochod")
    private Samochod samochod;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPracownik")
    private Pracownik pracownik;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idKontrahent")
    private  Kontrahent kontrahent;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Zlecenie_Czesc",
            joinColumns = { @JoinColumn(name = "idZlecenie") },
            inverseJoinColumns = { @JoinColumn(name = "idCzesc") }
    )
    Set<Czesc> czesci = new HashSet<>();
}
