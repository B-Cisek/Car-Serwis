package car.serwis.database.model;


import car.serwis.helpers.ZlecenieStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zlecenie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_zlecenie",
            updatable = false
    )
    private Long idZlecenie;


    @Column(
            name = "data_przyjecia",
            nullable = false
    )
    private LocalDate dataPrzyjecia;

    @Column(
            name = "opis_zlecenie",
            nullable = false
    )
    private String opisZlecenie;

    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private ZlecenieStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_samochod", referencedColumnName = "id_samochod")
    private Samochod samochod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pracownik", referencedColumnName = "id_pracownik")
    private Pracownik pracownik;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_kontrahent", referencedColumnName = "id_kontrahent")
    private Kontrahent kontrahent;

    public ZlecenieStatus getStatus() {
        return status;
    }

    public void setStatus(ZlecenieStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID Zlecenia: " + idZlecenie;
    }
}
