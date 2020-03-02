package com.example.springessentials.repository;

import com.example.springessentials.model.Contact;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ContactRepository extends PagingAndSortingRepository<Contact, String> {
    List<Contact> findByNameContaining (String name);
}
