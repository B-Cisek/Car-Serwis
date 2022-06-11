package car.serwis.database.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PozycjaFaktury {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_pozycja_faktury",
            updatable = false
    )
    private Long idPozycjaFaktury;

    @Column(
            name = "opis_pozycji",
            nullable = false
    )
    private String opisPozycji;

    @Column(
            name = "ilosc",
            nullable = false
    )
    private Integer ilosc;

    @Column(
            name = "cena",
            nullable = false
    )
    private Double cena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_faktura", referencedColumnName = "id_faktura")
    private Faktura faktura;


}
