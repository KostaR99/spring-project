<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<title>Profil korisnika</title>
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
<security:authorize access="isAuthenticated()">
<div class="w3-row-padding w3-padding-64 w3-container w3-center">
  <div class="w3-content">
      <div class = "w3-third w3-content center">    	
    	<img alt="" src="${pageContext.request.contextPath}/pictures/profilna1.png" class ="w3-image"><br><br>
    	<c:if test="${jesamLiAdmin.uloga.nazivUloge == 'Admin'}">
    	<c:if test="${korisnik.uloga.nazivUloge == 'Korisnik'}">
    	<form action="/Mreza/admin/banUser?idK=${korisnik.idkorisnik} " method = "post">
    		<input type="submit" class = "w3-black w3-button" value = "banuj korisnika"><br><br>
    	</form>
    	</c:if>
    	</c:if>
		<c:if test="${not empty ja }">
			<c:if test="${ja.uloga.nazivUloge == 'Korisnik' }">
			<c:if test="${korisnik.uloga.nazivUloge == 'Korisnik'}">
				<form action="/Mreza/user/zaprati?idK=${korisnik.idkorisnik} " method = "post">
    				<input type="submit" class = "w3-black w3-button" value = "Zaprati korisnika"><br><br>
    			</form>
    		</c:if>
    		</c:if>
    	</c:if>
    	<c:if test="${jesamLiAdmin.uloga.nazivUloge == 'Korisnik'}">
    		<c:if test="${empty ja }">
    			<form action="/Mreza/user/otprati?idK=${korisnik.idkorisnik} " method = "post">
    				<input type="submit" class = "w3-black w3-button" value = "Otprati korisnika"><br><br>
    			</form>
    		</c:if>
    	</c:if>
    	<a href="/Mreza/sviPratiociKorisnika?username=${korisnik.username}"  class="w3-button w3-black">Pratioci korisnika</a><br><br>
    	<a href="/Mreza/svaPracenjaKorisnika?username=${korisnik.username}"  class="w3-button w3-black">Pracenja korisnika</a><br><br>
    	<c:if test="${not empty status}">
    		<h3>Status</h3>
    		<p class = "w3-text-black">${status.sadrzaj}</p>
    	</c:if>
    </div>
    <div class="w3-twothird">
		<h1>Informacije</h1>
		<p class = "w3-text-grey">Korisnicko ime: ${korisnik.username }</p><br>
		<p class = "w3-text-grey">Email: ${korisnik.email }</p><br>
		<p class = "w3-text-grey">Naziv uloge korisnika: ${korisnik.uloga.nazivUloge}</p><br>
		<c:if test="${korisnik.uloga.nazivUloge == 'Korisnik'}">
			<a href="/Mreza/prikazSvihKonfKorisnika?username=${korisnik.username}"  class="w3-button w3-black">Konfiguracije korisnika</a>	
		</c:if>	
    </div>
  </div>
</div>
</security:authorize>

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



