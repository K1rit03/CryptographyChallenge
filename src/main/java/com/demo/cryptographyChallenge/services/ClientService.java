package com.demo.cryptographyChallenge.services;

import com.demo.cryptographyChallenge.entities.Client;
import com.demo.cryptographyChallenge.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepository;
    private final EncryptionService encryptionService;

    public ClientService(ClientRepository clientRepository, EncryptionService encryptionService) {
        this.clientRepository = clientRepository;
        this.encryptionService = encryptionService;

    }


    public Client save(Client client){
        try{
            client.setUserDocument(encryptionService.encrypt(client.getUserDocument()));
            client.setCreditCardToken(encryptionService.encrypt(client.getCreditCardToken()));
            return clientRepository.save(client);
        }catch (Exception e){
            throw new RuntimeException("Error saving client" + e.getMessage(), e);
        }
    }

    public Client findById(Client client){
        try{
            Optional<Client> clientOptional = clientRepository.findById(client.getId());
            if(clientOptional.isPresent()){
                Client clientDecrypted = clientOptional.get();
                clientDecrypted.setUserDocument(encryptionService.decrypt(clientDecrypted.getUserDocument()));
                clientDecrypted.setCreditCardToken(encryptionService.decrypt(clientDecrypted.getCreditCardToken()));
                return clientDecrypted;
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
            }
        } catch (Exception e){
            throw new RuntimeException("Error finding client" + e.getMessage(), e);
        }
    }

    public Client update(Long id, Client clientDetails){
        try {
            Optional<Client> clientOptional = clientRepository.findById(id);
            if(clientOptional.isPresent()){
                Client client = clientOptional.get();

                if(clientDetails.getUserDocument() != null){
                    clientDetails.setUserDocument(encryptionService.encrypt(clientDetails.getUserDocument()));
                }

                if(clientDetails.getCreditCardToken() != null){
                    clientDetails.setCreditCardToken(encryptionService.encrypt(client.getCreditCardToken()));
                }
                return clientRepository.save(client);
            } else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with the id: " + id);
            }
        }catch (Exception e) {
            throw new RuntimeException("Error updating the client: " + e.getMessage(), e);
        }
    }

    public void delete(Long id){
        try{
            if (clientRepository.existsById(id)){
                clientRepository.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with the id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting the client" + e.getMessage(), e);
        }
    }
}
