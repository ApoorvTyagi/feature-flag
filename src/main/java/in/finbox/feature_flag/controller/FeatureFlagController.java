package in.finbox.feature_flag.controller;

import in.finbox.feature_flag.model.FeatureFlag;
import in.finbox.feature_flag.service.FeatureFlagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flags")
@AllArgsConstructor
public class FeatureFlagController {
    private final FeatureFlagService service;

    @PostMapping("/create")
    public FeatureFlag create(@RequestParam String name, @RequestParam String description) {
        return service.createFlag(name, description);
    }

    @PostMapping("/dependency")
    public ResponseEntity<Void> addDependency(@RequestParam String parent, @RequestParam String child) {
        service.addDependency(parent, child);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/set")
    public ResponseEntity<Void> setFlag(@RequestParam Long clientId, @RequestParam String flag,
                                        @RequestParam boolean status) {
        service.setFlagStatus(clientId, flag, status);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/status")
    public boolean getStatus(@RequestParam Long clientId, @RequestParam String flag) {
        return service.getFlagStatus(clientId, flag);
    }

    @GetMapping("/enabled")
    public List<String> getEnabled(@RequestParam Long clientId) {
        return service.getEnabledFlags(clientId);
    }

    @GetMapping("/all")
    public List<FeatureFlag> getAll() {
        return service.getAllFlags();
    }
}
