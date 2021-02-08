<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<title>Prikaz prijave baga</title>
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
<header class="w3-container w3-red w3-center" style="padding:128px 16px">
  <h1 class="w3-margin w3-jumbo">PRIJAVA BAGA ID: ${prijava.idbagPrijave }</h1>

</header>

<!-- First Grid -->
<div class="w3-row-padding w3-padding-64 w3-container">
	<div class="w3-center w3-content w3-animate-bottom">
  		<p>Username korisnika koji je prijavio bag: ${username}</p>
  		<br><br>
  		<h4>Opis baga</h4>
  		<p class ="w3-text-grey">${prijava.opisBaga}</p>
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

