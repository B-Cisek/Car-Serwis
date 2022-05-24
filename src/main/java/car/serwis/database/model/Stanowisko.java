package car.serwis.database.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stanowisko {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_stanowisko",
            updatable = false
    )
    private Long idStanowisko;

    @Column(
            name = "nazwa_stanowiska",
            nullable = false
    )
    private String nazwaStanowiska;


    @OneToMany(mappedBy = "stanowisko")
    private List<Pracownik> pracownicy = new ArrayList<>();

    @Override
    public String toString() {
        return nazwaStanowiska;
    }
}