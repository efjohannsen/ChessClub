package com.kea.controllers;

public class Spoergsmål {
    /*
    * hvorfor Integer fremfor int i primary key?
    * 2x Override i Member model?
    * port i application properties ikke benyttet?
    * se jarls dokument samt weshop kode / one to many, osv.
    * hvordan skiftes H2 med en hosted SQL database
    * @table - er default at den tager navnet på klassen?
    *     @OneToOne( cascade = CascadeType.ALL,
            mappedBy = "companyProdDesc" )
    * Er column nødvendig?
    *     @JoinColumn(name = "companyproddesc_id")
    * @NotBlank?
    * bruges @ManyToMany nogensinde?
    * Query i repository?
    * JARLS noter om manytomany, ikke brug for at lave et nyt table
    * hvorfor ikke @autowired
    */
}
