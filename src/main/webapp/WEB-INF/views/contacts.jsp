<%@ include file="/WEB-INF/taglib.jsp" %>

<div id="googleMap"></div>
<div class="container body " id="contacts" style=>
  <h1>Contact us!</h1>
  <hr>
  <div class="row justify-content-md-between">
    
    <div class="col-md-6">
      <form:form action="/contacts" modelAttribute="contactModel" method="POST">
        <div class="form-group">
          <form:input path="name" cssClass="form-control" title="User name" placeholder="Name"></form:input>
		  <form:input path="email" cssClass="form-control" title="E-mail address" placeholder="Email Address"></form:input>
		  <form:textarea path="massage" cssClass="form-control" title="Your massage" placeholder="Massage (to 255 chars)"></form:textarea>
			<div class="row"> 
				<div class="g-recaptcha" data-sitekey="6LcJymMUAAAAAO3WrPEuEiadGbyqn13mT_oyvKYI"></div>
				<div class="col-sm-6"><input type="submit" class="btn btn-success btn-block" value="Submit"/></div>
			</div>
        </div>
      </form:form>
    </div>
    <div class="col-md-6 text-center">
      <div class="row">
        <i class="fas fa-map-marker-alt col-1"></i> 
        <span class="col-11">Pyotr Doroshenko street, 6 Ukraine, Lviv</span>
      </div>
      <div class="row">
        <i class="fas fa-phone col-1"></i>
        <ul class="col-11">
          <li>(093) 568-49-65</li>
          <li>(067) 852-24-62</li>
        </ul>
      </div>
      <div class="row">
        <i class="fas fa-envelope col-1"></i> 
        <a href="mailto:naviforceua.gmail.com" class="col-11">naviforceua.gmail.com</a>
      </div>
      <div class="row">
        <i class="fas fa-clock col-1"></i> 
        <ul class="col-11">
          <li>(Mon-Sat) - 10:00 to 21:00;</li>
          <li>Sunday - 10:00 to 19:00;</li>
        </ul> 
      </div>
    </div>
  </div>

</div>


<script>
    function myMap() {
    var myCenter = new google.maps.LatLng(49.8415395, 24.0268736);
    var mapProp = {center:myCenter, zoom:15, scrollwheel:false, draggable:false, mapTypeId:google.maps.MapTypeId.ROADMAP};
    var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
    var marker = new google.maps.Marker({position:myCenter});
    marker.setMap(map);
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBbx4HZXZqHotKXXxW1dXJ4cNH9Gwv0DlU&callback=myMap"></script>
