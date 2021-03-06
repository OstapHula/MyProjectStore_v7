package ua.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.springboot.web.entity.ContactEntity;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Integer>{

}
