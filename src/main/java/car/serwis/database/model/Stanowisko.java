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
public class Stanowisko {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stanowisko")
    private Long idStanowisko;

    @Column
    private String nazwa;

    @OneToMany(mappedBy="stanowisko")
    private List<Pracownik> pracownicy;


    public Stanowisko(String nazwa) {
        this.nazwa = nazwa;
    }
}
