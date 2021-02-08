<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<title>Prikaz konfiguracije</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
.fa-anchor,.fa-coffee {font-size:200px}
.rounded{
	border-radius: 25px;
	background-color: #f77b72;
	color:white;
}

.rounded ::placeholder{
	color:black;
}
</style>
<body>

<!-- Navbar -->
<security:authorize access="isAuthenticated()">
<div class="w3-top">
  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <a href="/Mreza/auth/pocetna" class="w3-bar-item w3-button w3-padding-large w3-red">PC MASTER RACE</a>
    <form action="/Mreza/pretraziKorisnika" method="get">
    	<input type="text" placeholder="username korisnika" class = "w3-bar-item w3-padding-large w3-hide-small rounded" name = "username">
    </form>
    <a href="/Mreza/mojProfil" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Moj profil</a>
	<a href="/Mreza/auth/logout" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Odjava</a>
	
  </div>

  <!-- Navbar on small screens -->

  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
    <a href="/Mreza/auth/pocetna" class="w3-bar-item w3-button w3-padding-large w3-red">PC MASTER RACE</a>
    <form action="/Mreza/PretraziKorisnika" method="get">
    		<input type="text" placeholder="username korisnika" class = "w3-bar-item w3-padding-large w3-gray" name = "username">
    </form>
    <a href="/Mreza/mojProfil" class="w3-bar-item w3-button  w3-padding-large w3-hover-white">Moj profil</a>
	<a href="/Mreza/auth/logout" class="w3-bar-item w3-button w3-padding-large w3-hover-white">Odjava</a>
  </div>
</div>
</security:authorize>

<!-- Header -->

<!-- First Grid -->
<div class="w3-row-padding w3-padding-64 w3-container">
  <div class="w3-content">
    <div class = "w3-third w3-content">    	
    	<img alt="" src="${pageContext.request.contextPath}/pictures/6849656.png" class ="w3-image">
    </div>
    <div class="w3-twothird">
		<h1>Informacije</h1>
		<p class = "w3-text-grey">Naziv konfiguracije: ${konfiguracija.nazivPrivKonf }</p><br>
		<p class = "w3-text-grey">Namena konfiguracije: ${konfiguracija.namena }</p><br>
		<p class = "w3-text-grey">komponente konfiguracije:</p><br>
		<c:if test="${not empty konfiguracija.komponentas }">
			<table class = "w3-table-all w3-bordered">
				<tr>
					<th>Naziv komponente</th>
					<th>Tip Komponente</th>
					<th>Detaljnije o komponenti</th>
				</tr>
				<c:forEach items="${konfiguracija.komponentas}" var = "k">
					<tr>
						<td>${k.nazivKomponente }</td>
						<td>${k.tipkomponente.nazivTipa }</td>
						<td><a href = "/Mreza/kompInfo?idKonf=${k.idkomponenta}">detaljnije</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
    </div>

  </div>
</div>

<!-- Second Grid -->

<script>
// Used to toggle the menu on small screens when clicking on the menu button
function myFunction() {
  var x = document.getElementById("navDemo");
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
  } else { 
    x.className = x.className.replace(" w3-show", "");
  }
}
</script>

</body>
</html>



l>