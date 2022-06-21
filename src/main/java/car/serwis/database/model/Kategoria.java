package car.serwis.database.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "kategoria", fetch = FetchType.LAZY, targetEntity = Czesc.class)
    private Set<Czesc> czesci = new HashSet<>();

    @Override
    public String toString() {
        return nazwaKategori;
    }
}