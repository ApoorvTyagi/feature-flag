package in.finbox.feature_flag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.finbox.feature_flag.model.*;

import java.util.List;

public interface FeatureFlagDependencyRepository extends JpaRepository<FeatureFlagDependency, Long> {
    List<FeatureFlagDependency> findByParentFlag(FeatureFlag parent);
    List<FeatureFlagDependency> findByChildFlag(FeatureFlag child);
}
