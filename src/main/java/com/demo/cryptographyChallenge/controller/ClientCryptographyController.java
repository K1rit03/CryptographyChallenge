package com.demo.cryptographyChallenge.controller;

import com.demo.cryptographyChallenge.entities.Client;
import com.demo.cryptographyChallenge.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/client")
public class ClientCryptographyController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client){
        try{
            return clientService.save(client);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating client" + e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id){
        try{
            return clientService.findById();
        } catch (ResponseStatusException e){
            throw e;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting client" + e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Client update(PathVariable Long id){
        try{
            return clientService.update(id);
        }
    }

}
