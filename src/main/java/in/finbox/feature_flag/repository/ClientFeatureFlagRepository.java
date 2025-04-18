package in.finbox.feature_flag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.finbox.feature_flag.model.*;

import java.util.List;
import java.util.Optional;

public interface ClientFeatureFlagRepository extends JpaRepository<ClientFeatureFlag, Long> {
    Optional<ClientFeatureFlag> findByClientAndFeatureFlag(Client client, FeatureFlag featureFlag);
    List<ClientFeatureFlag> findAllByClientAndStatusTrue(Client client);
}
