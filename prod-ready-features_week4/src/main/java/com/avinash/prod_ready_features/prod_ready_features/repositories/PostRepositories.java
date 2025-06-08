package com.avinash.prod_ready_features.prod_ready_features.repositories;

import com.avinash.prod_ready_features.prod_ready_features.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositories extends JpaRepository<PostEntity,Long> {
}
