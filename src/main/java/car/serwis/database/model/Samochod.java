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
public class Samochod {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_samochod")
    private Long idSamochod;

    @Column
    private String marka;

    @Column
    private String model;


    @OneToMany(mappedBy="samochod", fetch = FetchType.LAZY)
    private List<Czesc> czesci;

    @OneToOne(mappedBy = "samochod")
    private Zlecenie zlecenie;


    public Samochod(String marka, String model) {
        this.marka = marka;
        this.model = model;
    }
}
