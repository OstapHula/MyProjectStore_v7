<script src="${pageContext.request.contextPath}/resources/js/jquery-slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.min.js"></script> 
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src='https://www.google.com/recaptcha/api.js'></script>

<script>
  function slowScroll (id) {
    $('html, body').animate ({
      scrollTop: $(id).offset ().top
    }, 800);
    return false;
  }
</script>
<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip(); 
});
</script>
<script>
	$('#myTab a').on('click', function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	})
</script> 