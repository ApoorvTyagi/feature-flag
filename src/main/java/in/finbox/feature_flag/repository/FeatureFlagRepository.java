package in.finbox.feature_flag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.finbox.feature_flag.model.FeatureFlag;

import java.util.Optional;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Long> {
    Optional<FeatureFlag> findByName(String name);
}
