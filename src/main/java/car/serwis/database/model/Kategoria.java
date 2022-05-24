package car.serwis.database.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_kategoria",
            updatable = false
    )
    private Long idKategoria;


    @Column(
            name = "nazwa_kategori",
            nullable = false
    )
    private String nazwaKategori;

    @OneToMany(mappedBy = "kategoria", fetch = FetchType.LAZY)
    private Set<Czesc> czesci = new HashSet<>();
}