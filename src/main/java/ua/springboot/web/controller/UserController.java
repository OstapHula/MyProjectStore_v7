package ua.springboot.web.controller;


import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ua.springboot.web.domain.base.ChangePasswordRequest;
import ua.springboot.web.domain.user.EditUserRequest;
import ua.springboot.web.entity.AddresEntity;
import ua.springboot.web.entity.CardEntity;
import ua.springboot.web.entity.OrderEntity;
import ua.springboot.web.entity.ProductEntity;
import ua.springboot.web.entity.QuantityProductsEntity;
import ua.springboot.web.entity.UserEntity;
import ua.springboot.web.entity.enumeration.OrderStatus;
import ua.springboot.web.mapper.UserMapper;
import ua.springboot.web.service.AddressService;
import ua.springboot.web.service.CardService;
import ua.springboot.web.service.OrderService;
import ua.springboot.web.service.ProductService;
import ua.springboot.web.service.QuantityService;
import ua.springboot.web.service.UserService;
import ua.springboot.web.service.utils.CustomFileUtils;

@Controller
@RequestMapping({"/user", "/admin"})
@SessionAttributes("editUserModel")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private QuantityService quantityService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CardService cardService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String showUserProfile(Model model, Principal principal) throws IOException{
	UserEntity entity = userService.findUserByEmail(principal.getName());
	if(entity == null) return "redirect:/";
	
	entity.setImagePath(CustomFileUtils.getImage("user_" + entity.getId(), entity.getImagePath()));
	
	model.addAttribute("title", entity.getRole().getRole() + " profile");
	model.addAttribute("userProfile", UserMapper.entityUserToUserRequest(entity));
	model.addAttribute("addresesModel", entity.getAddreses());
	return "user/profile";
    }
        
    @GetMapping("/edit")
    public String editUserProfile(Model model, Principal principal){
	UserEntity entity = userService.findUserByEmail(principal.getName());	
	EditUserRequest request = UserMapper.entityUserToEditRequest(entity);

	model.addAttribute("title", "Edit profile page");
	model.addAttribute("addresesModel", entity.getAddreses());
	model.addAttribute("cardsModel", entity.getCards());
	model.addAttribute("editUserModel", request);
	return "user/edit";
    }
    
    @PostMapping("/edit")
    public String saveEditedProfile(@ModelAttribute ("editUserModel") EditUserRequest request) throws IOException{
	UserEntity entity = UserMapper.editRequestToUserEntity(request);
	userService.updateUser(entity);
	
	CustomFileUtils.createImage("user_" + entity.getId(), request.getFile());
	return "redirect:/user";
    }
    
    @GetMapping("/change-pass")
    public String showChangePass(Model model, Principal principal){
	UserEntity entity = userService.findUserByEmail(principal.getName());	
	ChangePasswordRequest request = UserMapper.entityUserToChangePasswordRequest(entity);
	
	model.addAttribute("title", "Change password page");
	model.addAttribute("changePassModel", request);
	return "user/change-pass";
    }
    
    @PostMapping("/change-pass")
    public String saveChangePass(
	    @ModelAttribute ("changePassModel") @Valid ChangePasswordRequest request,
	    BindingResult result){
	if(result.hasErrors()) return "user/change-pass";
	
	UserEntity entity = UserMapper.changePasswordRequestToUserEntity(request);
	userService.updateUser(entity);
	return "redirect:/user/edit";
    }
    
    @GetMapping("/add-address")
    public String showAddAddress(Model model){
	model.addAttribute("title", "Add address page");
	model.addAttribute("addAddressModel", new AddresEntity());
	return "user/add-address";
    }
    
    @PostMapping("/add-address")
    public String saveAddAddress(@ModelAttribute ("addAddressModel")  AddresEntity entity, 
	    Principal principal){
	UserEntity user = userService.findUserByEmail(principal.getName());
	entity.setUser(user);
	user.getAddreses().add(entity);
	
	addressService.saveAddress(entity);
	userService.updateUser(user);
	return "redirect:/user/edit";
    }
    
    @GetMapping("/add-card")
    public String showAddCard(Model model){
	model.addAttribute("title", "Add card page");
	model.addAttribute("addCardModel", new CardEntity());
	return "user/add-card";
    }
    
    @PostMapping("/add-card")
    public String saveAddCard(@ModelAttribute ("addCardModel")  CardEntity entity, 
	    Principal principal){
	UserEntity user = userService.findUserByEmail(principal.getName());
	entity.setUser(user);
	user.getCards().add(entity);
	
	cardService.saveCard(entity);
	userService.updateUser(user);
	return "redirect:/user/edit";
    }
    
    
    @GetMapping("/cart")
    public String showUserCart(Model model, Principal principal) throws IOException{
	UserEntity user = userService.findUserByEmail(principal.getName());
	OrderEntity order = new OrderEntity();
	List<QuantityProductsEntity> quantity = new ArrayList<>();
	if (!user.getOrders().isEmpty()) {
	    if(orderService.findOrderByStatus(OrderStatus.CART, user.getId()) != null){
		order = orderService.findOrderByStatus(OrderStatus.CART, user.getId());
		quantity = order.getQuantitys();
	    	for(QuantityProductsEntity entity : quantity) {
	    	    ProductEntity product = entity.getProduct();
	    	    product.setImagePath(CustomFileUtils.getImage("product_" + product.getId(), product.getImagePath()));
	    	}
	    }
	    
	}
	model.addAttribute("title", "My cart page");
	model.addAttribute("cartList", quantity);
	return "user/cart";
    }
    
    @GetMapping("/confirm-order")
    public String showConfirmOrder(Model model,Principal principal) throws IOException{
	UserEntity user = userService.findUserByEmail(principal.getName());
	OrderEntity order = orderService.findOrderByStatus(OrderStatus.CART, user.getId());
	
	for(QuantityProductsEntity quantity : order.getQuantitys()){
	    ProductEntity product = quantity.getProduct();
	    product.setImagePath(CustomFileUtils.getImage("product_" + product.getId(), product.getImagePath()));
	}

	model.addAttribute("title", "Confirm order page");
	model.addAttribute("orderModel", order);
	return "user/confirm-order";
    }
    
    @PostMapping("/confirm-order")
    public String saveConfirmOrder(@ModelAttribute("orderModel") OrderEntity order, Principal principal){
	UserEntity user = userService.findUserByEmail(principal.getName());
	OrderEntity entity = orderService.findOrderByStatus(OrderStatus.CART, user.getId());
	entity.setStatus(OrderStatus.CONFIRM);
	entity.setAddres(order.getAddres());
	entity.setCard(order.getCard());
	orderService.saveOrder(entity);
	return "user/payment";
    }

    
    @GetMapping("/cart/increment")
    public String incrementQuantity(@RequestParam("quantity") int quantity, @RequestParam("id") int id){
	QuantityProductsEntity qEntity = quantityService.findQuantityById(id);
	ProductEntity product = qEntity.getProduct();
	if(product.getInStock() >= 1) {
	    product.setInStock(product.getInStock() - 1);
	    qEntity.setQuantity(quantity + 1);
	}
	
	productService.saveProduct(product);
	quantityService.saveQuantity(qEntity);
	return "redirect:/user/cart";
    }
    
    @GetMapping("/cart/decrement")
    public String decrementQuantity(@RequestParam("quantity") int quantity, @RequestParam("id") int id){
	QuantityProductsEntity qEntity = quantityService.findQuantityById(id);
	ProductEntity product = qEntity.getProduct();
	if(quantity > 1){
	    product.setInStock(product.getInStock() + 1);
	    qEntity.setQuantity(quantity - 1);
	}
	
	productService.saveProduct(product);
	quantityService.saveQuantity(qEntity);
	return "redirect:/user/cart";
    }
    
    @GetMapping("/orders")
    public String showUserOrders(Model model, Principal principal){
	UserEntity user = userService.findUserByEmail(principal.getName());
	List<OrderEntity> orders = new ArrayList<>();
	if(!user.getOrders().isEmpty()){
	    orders = user.getOrders();
	}
	
	model.addAttribute("title", "My orders page");
	model.addAttribute("ordersModel", orders);
	return "user/orders";
    }
    
    @GetMapping("/favorite")
    public String showFavorite(Model model, Principal principal) throws IOException{
	UserEntity user = userService.findUserByEmail(principal.getName());
	Set<ProductEntity> products = user.getFavoriteProducts();
	for(ProductEntity product : products){
	    product.setImagePath(CustomFileUtils.getImage("product_"  + product.getId(), product.getImagePath()));
	}
	
	model.addAttribute("title", "Favorite products page");
	model.addAttribute("productsModel", products);
	return "user/favorite";
    }
    
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String showListUsers(Model model) throws IOException{
	model.addAttribute("title", "List users page");
	List<UserEntity> users = userService.findAllUsers();
	for(UserEntity user : users){
	    user.setImagePath(CustomFileUtils.getImage("user_" + user.getId(), user.getImagePath()));
	}
	model.addAttribute("userListModel", users);
	return "user/users";
    } 
    
    @GetMapping("/delete/user")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String showDelUser(@RequestParam("id") int id){
	if(id != 1) userService.deleteUserById(id);
	return "redirect:/user/users";
    }
    
    @GetMapping("/delete/quantity")
    public String showDelQuantity(@RequestParam("id") int id, Principal principal){
	UserEntity user = userService.findUserByEmail(principal.getName());
	
	QuantityProductsEntity quantity = quantityService.findQuantityById(id);
	ProductEntity product = quantity.getProduct();
	product.setInStock(product.getInStock() + quantity.getQuantity());
	quantityService.deleteQuantityById(id);
	productService.saveProduct(product);
	
	OrderEntity order = orderService.findOrderByStatus(OrderStatus.CART, user.getId());	
	if(order.getQuantitys().isEmpty()) orderService.deleteOrderById(order.getId());

	return "redirect:/user/cart";
    }
 
    @GetMapping("/delete/address")
    public String showDelAddress(@RequestParam("id") int id){
	AddresEntity address = addressService.findAddresById(id);
	if(address.getOrders().isEmpty()) addressService.deleteAdderssById(id);
	return "redirect:/user/edit";
    }
    
    @GetMapping("/delete/card")
    public String showDelCard(@RequestParam("id") int id){
	CardEntity card = cardService.findCardbyId(id);
	if(card.getOrders().isEmpty()) cardService.deleteCardById(id);
	return "redirect:/user/edit";
    }
}
