package com.avinash.SequirityApp.SequirityApp.repositories;

import com.avinash.SequirityApp.SequirityApp.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositories extends JpaRepository<PostEntity,Long> {
}

