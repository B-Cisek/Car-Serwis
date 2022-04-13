package car.serwis.database.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pracownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pracownik")
    private Long idPracownik;

    @Column
    private String imie;

    @Column
    private String nazwisko;

    @Column(name = "pracuje_od")
    private Date pracujeOd;

    @Column
    private String login;

    @Column
    private String haslo;

    @ManyToOne
    @JoinColumn(name="id_stanowisko", nullable=false)
    private Stanowisko stanowisko;
}
