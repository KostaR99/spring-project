<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<title>Inicijalizacija konfiguracije</title>
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
<security:authorize access="hasRole('Admin')">
<form action="/Mreza/admin/saveConv" method="post">
	<div class="w3-row-padding w3-padding-64 w3-container w3-center">
		<div class="w3-content1 center">
			<p>Naziv konfiguracije: </p><input name="nazivKonfiguracije" placeholder="Naziv konfiguracije" class = "w3-input" required="required"/><br><br>
			<p>Namena konfiguracije: </p><input name="namenaKonfiguracije" placeholder="Namena konfiguracije" class = "w3-input" required="required"/><br><br>
			<p>Maticna ploca: </p><select name="idMaticna" class = "w3-select" required="required"><c:forEach items="${mboard }" var="mbr"><option value = "${mbr.idkomponenta }">${mbr.nazivKomponente }</option></c:forEach></select><br><br>
			<p>CPU: </p><select name="idCPU" class = "w3-select" required="required"><c:forEach items="${cpus }" var="cpu" ><option value = "${cpu.idkomponenta }">${cpu.nazivKomponente }</option></c:forEach></select><br><br>
			<p>GPU: </p><select name="idGPU" class = "w3-select" required="required"><c:forEach items="${gpus }" var="gpu" ><option value = "${gpu.idkomponenta }">${gpu.nazivKomponente }</option></c:forEach></select><br><br>
			<p>RAM: </p><select name="idRam" class = "w3-select" required="required"><c:forEach items="${rams }" var="ram" ><option value = "${ram.idkomponenta }">${ram.nazivKomponente }</option></c:forEach></select><br><br>
			<p>HDD: </p><select name="idHDD" class = "w3-select" required="required"><c:forEach items="${hdds }" var="hdd" ><option value = "${hdd.idkomponenta }">${hdd.nazivKomponente }</option></c:forEach></select><br><br>
			<p>SSD: </p><select name="idSSD" class = "w3-select" required="required"><c:forEach items="${ssds }" var="ssd" ><option value = "${ssd.idkomponenta }">${ssd.nazivKomponente }</option></c:forEach></select><br><br>
			<p>Kuciste: </p><select name="idKuciste"  class = "w3-select" required="required"><c:forEach items="${cases }" var="case"><option value = "${case.idkomponenta }">${case.nazivKomponente }</option></c:forEach></select><br><br>
			<input type="submit" value="Sacuvaj konfiguraciju" class="w3-button w3-black">		
		</div>
	</div>
</form>
</security:authorize>
<security:authorize access="hasRole('Korisnik')">
<form action="/Mreza/user/saveConv" method="post">
	<div class="w3-row-padding w3-padding-64 w3-container w3-center">
		<div class="w3-content1 center">
			<p>Naziv konfiguracije: </p><input name="nazivKonfiguracije" placeholder="Naziv konfiguracije" class = "w3-input" required="required"/><br><br>
			<p>Namena konfiguracije: </p><input name="namenaKonfiguracije" placeholder="Namena konfiguracije" class = "w3-input" required="required"/><br><br>
			<p>Maticna ploca: </p><select name="idMaticna" class = "w3-select"><c:forEach items="${mboard }" var="mbr"><option value = "${mbr.idkomponenta }">${mbr.nazivKomponente }</option></c:forEach></select><br><br>
			<p>CPU: </p><select name="idCPU" class = "w3-select" required="required"><c:forEach items="${cpus}" var="cpu"><option value = "${cpu.idkomponenta }">${cpu.nazivKomponente }</option></c:forEach></select><br><br>
			<p>GPU: </p><select name="idGPU" class = "w3-select" required="required"><c:forEach items="${gpus }" var="gpu"><option value = "${gpu.idkomponenta }">${gpu.nazivKomponente }</option></c:forEach></select><br><br>
			<p>RAM: </p><select name="idRam" class = "w3-select" required="required"><c:forEach items="${rams }" var="ram" ><option value = "${ram.idkomponenta }">${ram.nazivKomponente }</option></c:forEach></select><br><br>
			<p>HDD: </p><select name="idHDD" class = "w3-select" required="required"><c:forEach items="${hdds }" var="hdd" ><option value = "${hdd.idkomponenta }">${hdd.nazivKomponente }</option></c:forEach></select><br><br>
			<p>SSD: </p><select name="idSSD" class = "w3-select" required="required"><c:forEach items="${ssds }" var="ssd" ><option value = "${ssd.idkomponenta }">${ssd.nazivKomponente }</option></c:forEach></select><br><br>
			<p>Kuciste: </p><select name="idKuciste"  class = "w3-select" required="required"><c:forEach items="${cases }" var="case"><option value = "${case.idkomponenta }">${case.nazivKomponente }</option></c:forEach></select><br><br>
			<input type="submit" value="Sacuvaj konfiguraciju" class="w3-button w3-black">		
		</div>
	</div>
</form>
</security:authorize>
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
