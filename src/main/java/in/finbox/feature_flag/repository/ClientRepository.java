package in.finbox.feature_flag.repository;

import in.finbox.feature_flag.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
