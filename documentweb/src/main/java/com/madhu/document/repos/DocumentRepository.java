package com.madhu.document.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madhu.document.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

}
