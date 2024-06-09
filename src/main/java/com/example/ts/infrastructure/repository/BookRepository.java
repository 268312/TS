package com.example.ts.infrastructure.repository;

import com.example.ts.infrastructure.entity.BookEntity;
import com.example.ts.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
}
