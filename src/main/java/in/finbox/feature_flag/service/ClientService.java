package in.finbox.feature_flag.service;

import in.finbox.feature_flag.model.Client;
import in.finbox.feature_flag.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepo;

    public Client createClient(String name) {
        Client client = new Client();
        client.setName(name);
        return clientRepo.save(client);
    }

    public Client getClient(Long id) {
        return clientRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }
}
