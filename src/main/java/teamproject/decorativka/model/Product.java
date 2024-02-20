package teamproject.decorativka.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private String country;
    private String producer;
    private String collection;
    private String tone;
    private String type;
    private String room;
    @Column(nullable = false)
    private String description;
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrl = new ArrayList<>();
    @Column(nullable = false)
    private boolean deleted = false;

    @PreRemove
    public void preRemove() {
        this.deleted = true;
    }
}
