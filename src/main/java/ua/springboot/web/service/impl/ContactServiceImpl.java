package ua.springboot.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.springboot.web.entity.ContactEntity;
import ua.springboot.web.repository.ContactRepository;
import ua.springboot.web.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{
    
    @Autowired private ContactRepository repository;
    
    @Override
    public void saveContact(ContactEntity entity) {
	repository.save(entity);
    }

    @Override
    public ContactEntity findContactById(int id) {
	return repository.findOne(id);
    }

    @Override
    public List<ContactEntity> findAllContacts() {
	return repository.findAll();
    }

}
