package com.example.springessentials.repository;

import com.example.springessentials.model.APIUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface APIUserRepository extends PagingAndSortingRepository<APIUser, String> {

    APIUser findByUsername (String username);
}
