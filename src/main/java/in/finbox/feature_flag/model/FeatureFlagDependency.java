package in.finbox.feature_flag.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "feature_flag_dependencies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
