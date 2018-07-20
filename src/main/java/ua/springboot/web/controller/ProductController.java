package ua.springboot.web.controller;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.springboot.web.domain.product.CreateProductRequest;
import ua.springboot.web.domain.product.EditProductRequest;
import ua.springboot.web.domain.product.ProductNameFilter;
import ua.springboot.web.entity.ParametersProductEntity;
import ua.springboot.web.entity.ProductEntity;
import ua.springboot.web.entity.enumeration.FaseColor;
import ua.springboot.web.entity.enumeration.FaseType;
import ua.springboot.web.entity.enumeration.MaterialBody;
import ua.springboot.web.entity.enumeration.MaterialStrap;
import ua.springboot.web.entity.enumeration.ProductStyle;
import ua.springboot.web.mapper.ProductMapper;
import ua.springboot.web.service.ProductService;
import ua.springboot.web.service.utils.CustomFileUtils;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/catalog")
    public String showPagebleProduct(Model model, @PageableDefault Pageable pegeable) throws IOException {
	Page<ProductEntity> page = productService.findAllProductsByPage(pegeable);
	
	for(ProductEntity entity : page) {
	  entity.setImagePath(CustomFileUtils.getImage("product_" + entity.getId(), entity.getImagePath()));
	}
	
	int currentPage = page.getNumber();
	int totalPage = page.getTotalPages() - 1;
	int begin = Math.max(0, currentPage - 2);
	int end = Math.min(currentPage + 2, totalPage);

	model.addAttribute("title", "Catalog page: " + (currentPage + 1));
	model.addAttribute("currentList", page);
	model.addAttribute("beginIndex", begin);
	model.addAttribute("endIndex", end);
	model.addAttribute("currentIndex", currentPage);
	model.addAttribute("totalIndex", totalPage);
	model.addAttribute("productsListByPageSize", page.getContent());

	return "product/products";
    }
    
    @GetMapping("/products/search")
    public String showProductsByNameFilter(Model model,
	    @PageableDefault Pageable pegeable,
	    @RequestParam("search") String search) throws IOException {
	Page<ProductEntity> page = productService.findProductByName(pegeable, new ProductNameFilter(search));
	
	for(ProductEntity entity : page) {
	    entity.setImagePath(CustomFileUtils.getImage("product_" + entity.getId(), entity.getImagePath()));
	}
	
	model.addAttribute("title", "search " + search + " page");
	model.addAttribute("productsListByPageSize", page.getContent());
	return "product/products";
    }

    @GetMapping("/getnerate/random")
    public String generateRandom() {

	for (int i = 0; i < 30; i++) {
	    ProductEntity product = new ProductEntity();
	    product.setName("Product_" + i);
	    product.setDescription("Product description_" + i);
	    product.setPrice(new BigDecimal(i * 10 + ".00"));
	    product.setInStock(i % 5);
	    product.setParameters(new ParametersProductEntity());

	    productService.saveProduct(product);
	}
	return "";
    }

    @GetMapping("/product/{productId}")
    public String showProduct(@PathVariable("productId") int productId, Model model) throws IOException {
	ProductEntity product = productService.findProductById(productId);
	product.setImagePath(CustomFileUtils.getImage(
		"product_" + product.getId(), product.getImagePath()));

	model.addAttribute("title", product.getName() + " page");
	model.addAttribute("productModel", product);
	return "product/product";
    }

    @GetMapping("/add-product")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String showCreateProduct(Model model) {
	model.addAttribute("title", "Create new product");

	model.addAttribute("styleModel", ProductStyle.values());
	model.addAttribute("materialStrapModel", MaterialStrap.values());
	model.addAttribute("materialBodyModel", MaterialBody.values());
	model.addAttribute("FaseTypeModel", FaseType.values());
	model.addAttribute("FaseColorModel", FaseColor.values());

	model.addAttribute("productModel", new CreateProductRequest());
	return "product/add-product";
    }

    @PostMapping("/add-product")
    public String saveCreatedProduct(@ModelAttribute("productModel") CreateProductRequest request) throws IOException {
	ProductEntity entity = ProductMapper.createProductRequestToProductEntity(request);

	productService.saveProduct(entity);

	CustomFileUtils.createFolder("product_" + entity.getId());
	CustomFileUtils.createImage("product_" + entity.getId(), request.getProductImage());

	return "redirect:/product/product/" + entity.getId();
    }
    
    @GetMapping("/edit/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String showEditProduct(@PathVariable("productId") int productId, Model model) {
	ProductEntity entity = productService.findProductById(productId);
	EditProductRequest product = ProductMapper.productEntityToEditProductRequest(entity, entity.getParameters());
	System.out.println("id: " + product.getProductId());
	model.addAttribute("title", "Edit product");

	model.addAttribute("styleModel", ProductStyle.values());
	model.addAttribute("materialStrapModel", MaterialStrap.values());
	model.addAttribute("materialBodyModel", MaterialBody.values());
	model.addAttribute("FaseTypeModel", FaseType.values());
	model.addAttribute("FaseColorModel", FaseColor.values());

	model.addAttribute("productModel", product);
	return "product/edit";
    }
    
    @PostMapping("/edit/{productId}")
    public String saveEditedProduct(@PathVariable("productId") int productId, 
	    @ModelAttribute("productModel") EditProductRequest request) throws IOException {
	ProductEntity entity = ProductMapper.editProductRequestToProductEntity(request);

	productService.saveProduct(entity);
	CustomFileUtils.createImage("product_" + entity.getId(), request.getFile());

	return "redirect:/product/product/" + entity.getId();
    }
    
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") int id){
	productService.delProductById(id);
	return "redirect:/product/catalog";
    }
    
}
