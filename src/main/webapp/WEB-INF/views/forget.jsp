<%@ include file="/WEB-INF/taglib.jsp" %>

<div class="container body logReg">
	
	<h1>Forget password</h1>
	<hr>
	<div class="row justify-content-center">
			<div class="col-md-6">
				<p></p>
				<form:form action="/forget" modelAttribute="forgetModel">
					
					<input name="email" type="email" class="form-control" title="E-mail address" placeholder="Email Address"/>
					<div class="row"><input type="submit" class="btn btn-primary btn-block" value="Send"></div>
					
				</form:form>
			</div>
	</div>
</div>