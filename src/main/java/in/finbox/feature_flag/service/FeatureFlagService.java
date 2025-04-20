package in.finbox.feature_flag.service;

import in.finbox.feature_flag.model.*;
import in.finbox.feature_flag.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FeatureFlagService {
    private final FeatureFlagRepository flagRepo;
    private final ClientRepository clientRepo;
    private final ClientFeatureFlagRepository clientFlagRepo;
    private final FeatureFlagDependencyRepository dependencyRepo;

    public FeatureFlag createFlag(String name, String description) {
        return flagRepo.save(FeatureFlag.builder().name(name).description(description).build());
    }

    public List<FeatureFlag> getAllFlags() {
        return flagRepo.findAll();
    }

    public void addDependency(String parent, String child) {
        FeatureFlag parentFlag = flagRepo.findByName(parent).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Parent flag not found"));
        FeatureFlag childFlag = flagRepo.findByName(child).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Child flag not found"));
        boolean exists = dependencyRepo.existsByParentFlagAndChildFlag(parentFlag, childFlag);
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dependency already exists between parent and child");
        }

        dependencyRepo.save(FeatureFlagDependency.builder()
                .parentFlag(parentFlag)
                .childFlag(childFlag)
                .build());
    }

    @Transactional
    public void setFlagStatus(Long clientId, String flagName, boolean status) {
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Client not found"));
        FeatureFlag flag = flagRepo.findByName(flagName).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Flag not found"));
        if (!status) { // Setting status to false
            List<FeatureFlagDependency> children = dependencyRepo.findByParentFlag(flag);
            for (FeatureFlagDependency dep : children) {
                setFlagStatus(clientId, dep.getChildFlag().getName(), false);
            }
        } else {
            List<FeatureFlagDependency> parents = dependencyRepo.findByChildFlag(flag);
            for (FeatureFlagDependency dep : parents) {
                FeatureFlag parentFlag = dep.getParentFlag();
                Optional<ClientFeatureFlag> cff = clientFlagRepo.findByClientAndFeatureFlag(client, parentFlag);
                if (cff.isEmpty() || !Boolean.TRUE.equals(cff.get().getStatus())) {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_REQUIRED,
                            "Parent flag must be ON before enabling " + flag.getName());
                }
            }
        }
        ClientFeatureFlag cff = clientFlagRepo.findByClientAndFeatureFlag(client, flag)
                .orElse(ClientFeatureFlag.builder().client(client).featureFlag(flag).build());
        cff.setStatus(status);
        clientFlagRepo.save(cff);
    }

    public boolean getFlagStatus(Long clientId, String flagName) {
        Client client = clientRepo.findById(clientId).orElseThrow();
        FeatureFlag flag = flagRepo.findByName(flagName).orElseThrow();
        return clientFlagRepo.findByClientAndFeatureFlag(client, flag)
                .map(ClientFeatureFlag::getStatus).orElse(false);
    }

    public List<String> getEnabledFlags(Long clientId) {
        Client client = clientRepo.findById(clientId).orElseThrow();
        List<ClientFeatureFlag> enabled = clientFlagRepo.findAllByClientAndStatusTrue(client);
        List<String> flags = new ArrayList<>();
        for (ClientFeatureFlag cff : enabled) flags.add(cff.getFeatureFlag().getName());
        return flags;
    }
}
