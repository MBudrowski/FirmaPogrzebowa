<%-- 
    Document   : index
    Created on : 2016-11-12, 17:53:02
    Author     : Maciek
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link rel="stylesheet" type="text/css" href="js/jquery.datetimepicker.min.css"/ >
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script src="js/jquery.datetimepicker.full.min.js"></script>
		<title>Zamawianie pogrzebu - Firma pogrzebowa</title>
    </head>
	<body>
		<header class="pageHeader">
			<a href="index.html"><h1 class="headerTitle">Firma pogrzebowa</h1></a>
			<h2 class="headerDesc">Usługi pogrzebowe w najlepszej cenie!</h2>
		</header>
		<nav class="pageMenu">
			<c:if test="${sessionScope.userData.isLoggedIn()}">
				<p class="loggedAs">Zalogowano jako ${sessionScope.userData.imie} ${sessionScope.userData.nazwisko}</p>
			</c:if>
			<c:if test="${not sessionScope.userData.isLoggedIn()}">
				<p class="loggedAs">Nie zalogowano</p>
			</c:if>
			<hr/>
			<p class="menuTitle">Nawigacja</p>
			<li class="navMenu">
				<ul class="navElement">
					<a href="index.html">Strona główna</a>
				</ul>
				<ul class="navElement">
					<a href="offer.html">Oferta</a>
				</ul>
				<ul class="navElement dropdown">
					<a href="#" class="dropdown-element">Sprzedaż</a>
					<div class="dropdown-content">
						<a class="navElement" href="coffins.html">Trumny</a>
						<a class="navElement" href="wreaths.html">Wieńce</a>
						<a class="navElement" href="candles.html">Znicze</a>
					</div>
				</ul>
				<ul class="navElement">
					<a href="notices.html">Nekrologi</a>
				</ul>
				<ul class="navElement">
					<a href="contact.html">Kontakt</a>
				</ul>
			</li>
			<hr/>
			<c:if test="${sessionScope.userData.isLoggedIn()}">
				<c:if test="${not sessionScope.userData.hasWorkerPrivs()}">
					<p class="menuTitle">Konto użytkownika</p>
					<li class="navMenu">
						<ul class="navElement">
							<a href="logout.html">Wyloguj się</a>
						</ul>
						<ul class="navElement">
							<a href="orders.html">Zamówienia</a>
						</ul>
						<ul class="navElement">
							<c:if test="${not empty sessionScope.cart}">
								<a id="cartLink" href="cart.html">Koszyk (${sessionScope.cart.numberOfItems})</a>
							</c:if>
							<c:if test="${empty sessionScope.cart}">
								<a id="cartLink" href="cart.html">Koszyk (0)</a>
							</c:if>
						</ul>
						<ul class="navElement">
							<a href="orderFuneral.html">Zamów pogrzeb</a>
						</ul>
					</li>
				</c:if>
				<c:if test="${sessionScope.userData.hasWorkerPrivs()}">
					<p class="menuTitle">Konto pracownika</p>
					<li class="navMenu">
						<ul class="navElement">
							<a href="logout.html">Wyloguj się</a>
						</ul>
						<ul class="navElement">
							<a href="panel/index.html">Panel pracownika</a>
						</ul>
					</li>
				</c:if>
			</c:if>
			<c:if test="${not sessionScope.userData.isLoggedIn()}">
				<p class="menuTitle">Konto użytkownika</p>
				<li class="navMenu">
					<ul class="navElement">
						<a href="login.html">Logowanie</a>
					</ul>
					<ul class="navElement">
						<a href="register.html">Rejestracja</a>
					</ul>
				</li>
			</c:if>
		</nav>
        <section class="pageContent">
			<h2>Zamawianie pogrzebu</h2>
			<c:if test="${not empty funeralError}">
				<p class="errorMsg">${funeralError}</p>
			</c:if>
			<form method="POST" action="orderFuneral.html">
				<table>
					<tbody>
						<tr>
							<td colspan="2"><h3>Dane denata:</h3></td>
						</tr>
						<tr>
							<td>Imię:</td>
							<td><input type="text" name="name" value="${oldName}" /></td>
						</tr>
						<tr>
							<td>Nazwisko:</td>
							<td><input type="text" name="surname" value="${oldSurname}" /></td>
						</tr>
						<tr>
							<td>Adres:</td>
							<td><textarea name="address" rows="2" cols="40" style="resize: none; font-family: Arial; font-size: 12px;">${oldAddress}</textarea></td>
						</tr>
						<tr>
							<td>Data urodzenia:</td>
							<td><input class="datePicker" type="text" name="birthDate" value="${oldBirth}" placeholder="RRRR-MM-DD" /></td>
						</tr>
						<tr>
							<td>Data śmierci:</td>
							<td><input class="datePicker" type="text" name="deathDate" value="${oldDeath}" placeholder="RRRR-MM-DD" /></td>
						</tr>
						<tr>
							<td colspan="2"><h3>Dane dotyczące pogrzebu:</h3></td>
						</tr>
						<tr>
							<td>Dom pogrzebowy:</td>
							<td>
								<select name="domPogrzebowy">
									<c:forEach var="dom" items="${domyPogrzebowe}">
										<c:if test="${dom.id eq oldDom}">
											<option value="${dom.id}" selected>${dom.adres}</option>
										</c:if>
										<c:if test="${dom.id ne oldDom}">
											<option value="${dom.id}">${dom.adres}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>Kościół:</td>
							<td>
								<select name="kosciol">
									<c:forEach var="kos" items="${koscioly}">
										<c:if test="${kos.id eq oldKos}">
											<option value="${kos.id}" selected>${kos.adres}</option>
										</c:if>
										<c:if test="${kos.id ne oldKos}">
											<option value="${kos.id}">${kos.adres}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>Cmentarz:</td>
							<td>
								<select name="cmentarz">
									<c:forEach var="cm" items="${cmentarze}">
										<c:if test="${cm.id eq oldCm}">
											<option value="${cm.id}" selected>${cm.adres}</option>
										</c:if>
										<c:if test="${cm.id ne oldCm}">
											<option value="${cm.id}">${cm.adres}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>Wybrana trumna:</td>
							<td>
								<select name="trumna">
									<c:if test="${tr.id eq oldTr}">
										<option value="0" selected>We własnym zakresie</option>
									</c:if>
									<c:if test="${tr.id ne oldTr}">
										<option value="0">We własnym zakresie</option>
									</c:if>
									<c:forEach var="tr" items="${trumny}">
										<c:if test="${tr.id eq oldTr}">
											<option value="${tr.id}" selected>${tr.nazwa}</option>
										</c:if>
										<c:if test="${tr.id ne oldTr}">
											<option value="${tr.id}">${tr.nazwa}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td/>
							<td><input type="submit" value="Zamów pogrzeb"/></td>
						</tr>
					</tbody>
				</table>
			</form>
        </section>
		<footer class="pageFooter">Kopyrajt &copy; 2016 by Maciej Budrowski </footer>
	</body>
	<script type="text/javascript" src="js/datepicker.js"></script>
</html>