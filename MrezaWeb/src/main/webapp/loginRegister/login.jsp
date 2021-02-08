<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<title>login</title>
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
</style>
<body>

<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
   	<a href="/Mreza/" class="w3-bar-item w3-button w3-padding-large w3-red">PC MASTER RACE</a>
    <a href="/Mreza/auth/loginPage" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Prijavi se</a>
    <a href="/Mreza/auth/registerUser" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Registruj se</a>
  </div>

  <!-- Navbar on small screens -->
  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
    <a href="/Mreza/" class="w3-bar-item w3-button w3-padding-large w3-red">PC MASTER RACE</a>
    <a href="/Mreza/auth/loginPage" class="w3-bar-item w3-button w3-padding-large w3-hover-white">Prijavi se</a>
    <a href="/Mreza/auth/registerUser" class="w3-bar-item w3-button w3-padding-large w3-hover-white">Registruj se</a>
  </div>
</div>

<!-- Header -->
<header class="w3-container w3-red w3-center" style="padding:128px 16px">
  <h1 class="w3-margin w3-jumbo">LOGIN</h1>
</header>

<!-- First Grid -->
<div class="w3-row-padding w3-padding-64 w3-container w3-center">
  <div class="w3-content1">
	<c:url var="loginUrl" value="/login" />
	<form action="${loginUrl}" method="post">
		
			
				<p>Korisnicko ime: </p><input type="text" name="username"
					placeholder="Unesite korisnicko ime" required class = "w3-input"><br>
				<p>Sifra: </p><input type="password" name="password"
					placeholder="Unesite sifru" required class = "w3-input"><br>
                <p>Zapamti me: <input type="checkbox" name="remember-me"/></p><br>
				<input type="submit" value="Prijava" class = "w3-button w3-black"><br>
		Nemate nalog? <a href="/Mreza/auth/registerUser">Registrujte se</a>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
  </div>
</div>

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

