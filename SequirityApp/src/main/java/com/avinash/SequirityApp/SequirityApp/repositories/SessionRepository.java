package com.avinash.SequirityApp.SequirityApp.repositories;

import com.avinash.SequirityApp.SequirityApp.entities.Session;
import com.avinash.SequirityApp.SequirityApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {


   List<Session>findByUser(User user);

   Optional<Session> findByRefreshToken(String refreshToken);


}
