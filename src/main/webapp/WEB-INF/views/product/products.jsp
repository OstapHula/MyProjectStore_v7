<%@ include file="/WEB-INF/taglib.jsp" %>

	<c:url var="firstUrl" value="/product/catalog/?page=0" />
	<c:url var="lastUrl" value="/product/catalog/?page=${totalIndex}" />
	<c:url var="prevUrl" value="/product/catalog/?page=${currentIndex - 1}" />
	<c:url var="nextUrl" value="/product/catalog/?page=${currentIndex + 1}" />

<div class="container body products">
	
	<div class="row justify-content-between">
		<nav>
		  <ul class="pagination">
		  	<c:choose>
				<c:when test="${currentIndex == 0}">
					<li class="page-item disabled"><span class="page-link">&laquo;</span></li>
					<li class="page-item disabled"><span class="page-link">Previous</span></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="${firstUrl}">&laquo;</a></li>
					<li class="page-item"><a class="page-link" href="${prevUrl}">Previous</a></li>
				</c:otherwise>
			</c:choose>
			<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
				<c:url var="pageUrl" value="${rootUrl}/product/catalog/?page=${i}" />
				<c:choose>
					<c:when test="${currentIndex == i}">
						<li class="page-item active"><span class="page-link"><c:out value="${i+1}"/></span></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="${pageUrl}"><c:out value="${i+1}"/></a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		    <c:choose>
				<c:when test="${currentIndex == totalIndex}">
					<li class="page-item disabled"><span class="page-link" >Next</span></li>
					<li class="page-item disabled"><span class="page-link" >&raquo;</span></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="${nextUrl}">Next</a></li>
					<li class="page-item"><a class="page-link" href="${lastUrl}">&raquo;</a></li>
				</c:otherwise>
			</c:choose>
		  </ul>
		</nav>
		
		<form action="/product/products/search" class="form-inline">
			<input type="text" name="search" placeholder="Type title product for search" class="form-control">
			<input type="submit" value="Search" class="btn btn-primary">
		</form>
	</div>
	
	<hr>
	
	<div class="row listProducts">
	  <sec:authorize access="isAuthenticated()">
	  	<sec:authentication property="principal.username" var="username" />
	  </sec:authorize>
   	  <c:forEach items="${productsListByPageSize}" var="product">
		<div class="element-product">
		   <div class="card bg-light border-light">
		   
		   	<c:if test="${product.usersLike.isEmpty()}">
		   		<a href="/like?id=${product.id}">
   		 			<i class="far fa-heart heart animated pulse" id="animated"></i>
   		 		</a>
		   	</c:if>
		   	<c:forEach items="${product.usersLike}" var="user">
		   		 <c:choose>
		   		 	<c:when test="${user.email == username}">
		   		 		<a href="/dislike?id=${product.id}">
		   		 			<i class="fas fa-heart heart animated pulse" id="animated"></i>
		   		 		</a>
		   		 	</c:when>
		   		 	<c:otherwise>
		   		 		<a href="/like?id=${product.id}">
		   		 			<i class="far fa-heart heart animated pulse" id="animated"></i>
		   		 		</a>
		   		 	</c:otherwise>
		   		 </c:choose>
		   	 </c:forEach>
           	 
             <a href="/product/product/${product.id}">
              	<img src="data:image/png; base64, ${product.imagePath}" alt="product" class="card-img-top">
             </a>
             <hr>
             <div class="card-body">      
               <span class="badge badge-info">In stock: ${product.inStock}</span>  
               <a href="/product/product/${product.id}" class="text-muted">
               		<h5 class="card-title">${product.name}</h5>
               </a>              	               
	           <h3 class="price">Price: ${product.price} <i class="fas fa-dollar-sign"></i></h3>	               
             </div>
             <div class="btn-group" role="group"> 
               <c:choose>
               	<c:when test="${username == 'admin@gmail.com'}">
               		<a href="/product/edit/${product.id}" class="btn btn btn-warning">
               			<i class="fas fa-edit"></i> Edit
               		</a>
               		<a href="/product/delete?id=${product.id}" class="btn btn-danger">
               			<i class="fas fa-trash-alt"></i> Delete
               		</a>
               	</c:when>
               	<c:otherwise>
               		<a href="/product/product/${product.id}" class="btn btn-secondary">
               			<i class="fas fa-info"></i> Details
               		</a>
               		<c:choose>
               			<c:when test="${product.inStock <= 0}">
               				<a href="#" class="btn btn-success disabled">
			               		<i class="fas fa-shopping-basket"></i> Add to cart
			             	</a>
               			</c:when>
               			<c:otherwise>
               				<a href="/order/add-to-cart?id=${product.id}" class="btn btn-success">
		               			<i class="fas fa-shopping-basket"></i> Add to cart
		               		</a>
               			</c:otherwise>
               		</c:choose>
               	</c:otherwise>
               </c:choose>              
             </div>
           </div>
		</div>
	  </c:forEach>
	</div>
	
	<hr>
	
	<div class="row">
		<nav>
		  <ul class="pagination">
		  	<c:choose>
				<c:when test="${currentIndex == 0}">
					<li class="page-item disabled"><span class="page-link">&laquo;</span></li>
					<li class="page-item disabled"><span class="page-link">Previous</span></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="${firstUrl}">&laquo;</a></li>
					<li class="page-item"><a class="page-link" href="${prevUrl}">Previous</a></li>
				</c:otherwise>
			</c:choose>
			<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
				<c:url var="pageUrl" value="${rootUrl}/product/catalog/?page=${i}" />
				<c:choose>
					<c:when test="${currentIndex == i}">
						<li class="page-item active"><span class="page-link"><c:out value="${i+1}"/></span></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="${pageUrl}"><c:out value="${i+1}"/></a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		    <c:choose>
				<c:when test="${currentIndex == totalIndex}">
					<li class="page-item disabled"><span class="page-link" >Next</span></li>
					<li class="page-item disabled"><span class="page-link" >&raquo;</span></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="${nextUrl}">Next</a></li>
					<li class="page-item"><a class="page-link" href="${lastUrl}">&raquo;</a></li>
				</c:otherwise>
			</c:choose>
		  </ul>
		</nav>
	</div>
</div>
<a href="#" class="btn btn-light btn-block btn-lg backtt up-arrow" data-toggle="tooltip" title="TO TOP">
   <i class="fas fa-angle-double-up animated fadeInUp" id="animated"></i> Back to top
</a>
