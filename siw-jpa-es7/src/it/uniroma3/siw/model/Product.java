package it.uniroma3.siw.model; // nel package model mettiamo tutte le classi che hanno a che fare con il modello, entità

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Column;

@Entity
@NamedQuery(name = "findAllProducts", query = "SELECT p FROM Product p") //si utilizza nel test
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Float price;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String code;
    
    public Product() {
    }

    public Product(String name, Float price, String description, String code) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    public boolean equals(Object obj) {
        Product product = (Product)obj;
        return this.getCode().equals(product.getCode());
   }

    public int hashCode() {
        return this.code.hashCode();
     }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Product");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name);
        sb.append(", price=").append(price);
        sb.append(", description='").append(description);
        sb.append(", code='").append(code);
        sb.append("}\n");
        return sb.toString();
    }
}
