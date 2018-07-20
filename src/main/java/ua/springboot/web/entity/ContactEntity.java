package ua.springboot.web.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="contact")
@Getter
@Setter
@NoArgsConstructor
public class ContactEntity extends BaseEntity{
    private String name;
    private String email;
    private String massage;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
