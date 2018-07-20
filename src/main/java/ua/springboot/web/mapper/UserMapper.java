package ua.springboot.web.mapper;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import ua.springboot.web.domain.base.ChangePasswordRequest;
import ua.springboot.web.domain.base.LoginRequest;
import ua.springboot.web.domain.base.RegisterRequest;
import ua.springboot.web.domain.user.EditUserRequest;
import ua.springboot.web.domain.user.UserProfileRequest;
import ua.springboot.web.entity.UserEntity;
import ua.springboot.web.entity.enumeration.UserRole;

public interface UserMapper {

	public static User toSecurityUser(UserEntity entity) {
		return new User(entity.getEmail(), 
				entity.getPassword(), 
				AuthorityUtils.createAuthorityList(String.valueOf(entity.getRole())));
	}
	
	public static UserEntity registerRequestToUserEntity (RegisterRequest request){
		UserEntity entity = new UserEntity();
		
		entity.setFirstName(request.getFirstName());
		entity.setEmail(request.getEmail());
		entity.setPassword(request.getPassword());
		entity.setRole(UserRole.ROLE_USER);
		
		return entity;
	}
	
	public static UserEntity loginRequestToUserEntity (LoginRequest request){
	    UserEntity entity = new UserEntity();
	    
	    entity.setEmail(request.getEmail());
	    entity.setPassword(request.getPassword());
	    
	    return entity;
	}

	public static UserProfileRequest entityUserToUserRequest(UserEntity entity){
	    UserProfileRequest request = new UserProfileRequest();
	    
	    request.setEmail(entity.getEmail());
	    request.setFirstName(entity.getFirstName());
	    request.setLastName(entity.getLastName());
	    request.setTelephone(entity.getTelephone());
	    request.setAge(entity.getAge());
	    request.setImagePath(entity.getImagePath());
	    request.setIsActivated(entity.getIsActivated());
	    request.setRole(entity.getRole());
	    
	    return request;
	}
	
	public static EditUserRequest entityUserToEditRequest(UserEntity entity){
	    EditUserRequest request = new EditUserRequest();
	    
	    request.setId(entity.getId());
	    request.setEmail(entity.getEmail());
	    request.setFirstName(entity.getFirstName());
	    request.setLastName(entity.getLastName());
	    request.setTelephone(entity.getTelephone());
	    request.setAge(entity.getAge());
	    request.setRole(entity.getRole());
	    request.setPassword(entity.getPassword());
	    request.setIsActivated(entity.getIsActivated());
	    request.setOldImage(entity.getImagePath());
	   
	    return request;
	}
	
	public static UserEntity editRequestToUserEntity(EditUserRequest request){
	    UserEntity entity = new UserEntity();
	    
	    entity.setId(request.getId());
	    entity.setEmail(request.getEmail());
	    entity.setFirstName(request.getFirstName());
	    entity.setLastName(request.getLastName());
	    entity.setTelephone(request.getTelephone());
	    entity.setAge(request.getAge());
	    entity.setRole(request.getRole());
	    entity.setPassword(request.getPassword());
	    entity.setIsActivated(request.getIsActivated());
	    
	    if(!request.getFile().isEmpty()) { 
	    	entity.setImagePath(request.getFile().getOriginalFilename());
	    } else {
	    	entity.setImagePath(request.getOldImage());
	    }
	    
	    return entity;
	}
	
	public static ChangePasswordRequest entityUserToChangePasswordRequest(UserEntity entity){
	    ChangePasswordRequest request = new ChangePasswordRequest();
	    
	    request.setId(entity.getId());
	    request.setEmail(entity.getEmail());
	    request.setFirstName(entity.getFirstName());
	    request.setLastName(entity.getLastName());
	    request.setTelephone(entity.getTelephone());
	    request.setAge(entity.getAge());
	    request.setRole(entity.getRole());
	    request.setImagePath(entity.getImagePath());
	    request.setIsActivated(entity.getIsActivated());
	    
	    return request;
	}
	
	public static UserEntity changePasswordRequestToUserEntity(ChangePasswordRequest request){
	    UserEntity entity = new UserEntity();
	    
	    entity.setId(request.getId());
	    entity.setEmail(request.getEmail());
	    entity.setFirstName(request.getFirstName());
	    entity.setLastName(request.getLastName());
	    entity.setTelephone(request.getTelephone());
	    entity.setAge(request.getAge());
	    entity.setRole(request.getRole());	    
	    entity.setIsActivated(request.getIsActivated());
	    entity.setImagePath(request.getImagePath());
	    
	    entity.setPassword(request.getNewPassword());
	    
	    return entity;
	}
}
