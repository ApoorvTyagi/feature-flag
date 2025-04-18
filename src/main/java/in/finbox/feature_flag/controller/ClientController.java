package in.finbox.feature_flag.controller;

import in.finbox.feature_flag.model.Client;
import in.finbox.feature_flag.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService service;

    @PostMapping
    public Client create(@RequestParam String name) {
        return service.createClient(name);
    }

    @GetMapping
    public Client create(@RequestParam Long id) {
        return service.getClient(id);
    }
}
