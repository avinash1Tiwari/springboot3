package com.avinash.project.uber.uberApp.repositories;

import com.avinash.project.uber.uberApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
