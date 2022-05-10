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
public class Jednostka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jednostka")
    private Long idJednostka;

    @Column
    private String nazwaJednostki;

    @Column
    private String skrot;

    @OneToMany(mappedBy="jednostka", fetch = FetchType.LAZY)
    private List<Czesc> czesci;

    public Jednostka(String nazwaJednostki, String skrot) {
        this.nazwaJednostki = nazwaJednostki;
        this.skrot = skrot;
    }
}
