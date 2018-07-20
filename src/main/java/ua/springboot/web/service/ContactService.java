package ua.springboot.web.service;

import java.util.List;

import ua.springboot.web.entity.ContactEntity;


public interface ContactService {
    
    void saveContact(ContactEntity entity);
    
    ContactEntity findContactById(int id);
    
    List<ContactEntity> findAllContacts();
}
