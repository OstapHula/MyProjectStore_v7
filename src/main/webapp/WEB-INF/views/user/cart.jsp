<%@ include file="/WEB-INF/taglib.jsp" %>

<div class="container body orderList">
   <div class="row">
   
     <%@ include file="/WEB-INF/fragments/_leftnav.jsp" %>
     
     <div class="col-lg-9">			
		<h2>My cart</h2>
		<hr>
		<c:set var = "sum" scope = "session" value = "${0}"/>
		<div style="overflow-x: auto">
			<table class="table table-sm table-bordered ">
			  <thead class="thead-light">
			    <tr>
			      	<th>Image</th>
					<th>Title</th>
					<th>Create At</th>
					<th>price</th>
					<th>Quantity</th>
					<th>Total</th>
					<th>Delete</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach items="${cartList}" var="quantity">
				    <tr>
				      <td scope="row"><img src="data:image/png; base64, ${quantity.product.imagePath}" alt="image product" class="list-cart-img" ></td>
				      <td><a href="/product/product/${quantity.product.id}" class="text-muted">${quantity.product.name}</a></td>
				      <td>${quantity.product.createdAt}</td>
					  <td>${quantity.product.price} <i class="fas fa-dollar-sign"></i></td>					  
					  <td>
					  	<div class="btn-group" role="group" >
					  	  <a href="/user/cart/decrement?quantity=${quantity.quantity}&id=${quantity.id}" role="button" class="btn btn-secondary">
						  	<i class="fas fa-minus"></i>
						  </a>
						  <div class="quantity">${quantity.quantity}</div>
						  <a href="/user/cart/increment?quantity=${quantity.quantity}&id=${quantity.id}" role="button" class="btn btn-secondary">
						  	<i class="fas fa-plus"></i>
						  </a>
						</div>
					  </td> 
					  <td>${quantity.product.price*quantity.quantity} <i class="fas fa-dollar-sign"></i></td>
					  <td>
					  	<a href="/user/delete/quantity?id=${quantity.id}" class="btn btn-danger btn-block"><i class="fas fa-trash-alt"></i> Delete</a>
					  </td>
				    </tr>
				    <c:set var = "sum" scope = "session" value = "${sum + quantity.product.price*quantity.quantity}"/>
				</c:forEach>
				<tr>
					<c:choose>
						<c:when test="${cartList.isEmpty()}">
							<td colspan="6"><h5>Your cart is empty</h5></td>
							<td>
								<a href="/product/catalog" class="btn btn-primary btn-block">
									<i class="fas fa-shopping-cart"></i> Go to shop!
								</a>
							</td>
						</c:when>
						<c:otherwise>
							<td colspan="5"><h5>Confirm the order</h5></td>
							<td><c:out value = "${sum}"/><i class="fas fa-dollar-sign"></i></td>
							<td>
								<a href="/user/confirm-order" class="btn btn-success btn-block">
									<i class="fas fa-clipboard-check"></i> Confirm order
								</a>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			  </tbody>
			</table>
		</div>
	</div>
  </div>
</div>