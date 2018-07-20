package ua.springboot.web.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card")
@NoArgsConstructor
@Getter
@Setter
public class CardEntity extends BaseEntity {
    
    @Column(name = "card_number")
    private long cardNumber;
    
    @Column(name = "validity_period_mm")
    private int validityPeriodMM;
    
    @Column(name = "validity_period_yy")
    private int validityPeriodYY;
    
    @Column(name = "security_code")
    private int securityCode;
    
    @Column(name = "cardowner_first_name")
    private String cardownerFirstName;
    
    @Column(name = "cardowner_last_name")
    private String cardownerLastName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @OneToMany(mappedBy = "addres")
    private List<OrderEntity> orders = new ArrayList<>();
}