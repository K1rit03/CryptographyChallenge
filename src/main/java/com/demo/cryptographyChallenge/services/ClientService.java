package com.demo.cryptographyChallenge.services;

import com.demo.cryptographyChallenge.entities.Client;
import com.demo.cryptographyChallenge.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
                throw new RuntimeException("Client not found");
            }
        } catch (Exception e){
            throw new RuntimeException("Error finding client" + e.getMessage(), e);
        }
    }
}
