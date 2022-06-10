package car.serwis.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faktura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_faktura",
            updatable = false
    )
    private Long idFaktura;

    @Column(
            name = "numer_faktury",
            nullable = false
    )
    private String numerFaktury;

    @Column(
            name = "miejsce_wystawienia",
            nullable = false
    )
    private String miejsceWystawienia;

    @Column(
            name = "data_wystawienia",
            nullable = false
    )
    private LocalDate dataWystawienia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_kontrahent", referencedColumnName = "id_kontrahent")
    private Kontrahent kontrahent;

    @OneToMany(mappedBy = "faktura", fetch = FetchType.EAGER)
    private Set<PozycjaFaktury> pozycjeFaktury = new HashSet<>();

}
