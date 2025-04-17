package in.finbox.feature_flag.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "feature_flag_dependency")
@Data
@Builder
public class FeatureFlagDependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FeatureFlag parentFlag;

    @ManyToOne
    private FeatureFlag childFlag;
}
