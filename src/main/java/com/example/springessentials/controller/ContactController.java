package com.example.springessentials.controller;

import com.example.springessentials.model.Contact;
import com.example.springessentials.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("contact")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable){
        return new ResponseEntity<>(contactRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id,
                                      @AuthenticationPrincipal UserDetails userDetails){
        System.out.println(userDetails);
        return new ResponseEntity<>(contactRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name){
        return new ResponseEntity<>(contactRepository.findByNameContaining(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save (@Valid @RequestBody Contact contact) {
        return new ResponseEntity<>(contactRepository.save(contact), HttpStatus.OK);
    }

    @PostMapping("/many")
    @Transactional
    public ResponseEntity<?> saveMany (@RequestBody List<Contact> contacts) {
        for(Contact contact : contacts){
            if(contact.getName().equals("ERROR")){
                throw new RuntimeException("Invalid name");
            }
            contactRepository.save(contact);
        }
        return new ResponseEntity<>("Saved " + contacts.size() + " contact(s)", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete (@PathVariable("id") String id){
        contactRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
