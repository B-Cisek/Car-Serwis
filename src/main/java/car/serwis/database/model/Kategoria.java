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
public class Kategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kategoria")
    private Long idKategoria;

    @Column
    private String nazwaKategori;

    @OneToMany(mappedBy="kategoria", fetch = FetchType.EAGER)
    private List<Czesc> czesci;
}
