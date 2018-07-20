package ua.springboot.web.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import ua.springboot.web.entity.enumeration.UserRole;

@NoArgsConstructor
@Getter
@Setter
public class EditUserRequest {

    	private int id;
    	private String email;
	private String firstName;
	private String lastName;
	private String telephone;
	private int age;
	private UserRole role;
	private MultipartFile file;
	private String oldImage;
	private String password;
	private String isActivated;
    
}
