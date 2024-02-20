package teamproject.decorativka.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @EqualsAndHashCode.Exclude
    @ElementCollection
    @CollectionTable(name = "offer_images", joinColumns = @JoinColumn(name = "offer_id"))
    @Column(name = "image_url")
    private List<String> imageUrl = new ArrayList<>();
    @Column(nullable = false)
    private boolean deleted = false;

    @PreRemove
    public void preRemove() {
        this.deleted = true;
    }
}
