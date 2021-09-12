package com.yapily.interview.repository;

import com.yapily.interview.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KeysRepository extends JpaRepository<Key, Integer> { }
