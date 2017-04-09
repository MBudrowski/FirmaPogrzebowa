<%-- 
    Document   : index
    Created on : 2016-11-12, 17:53:02
    Author     : Maciek
--%>
<%@page import="mbud.hibernate.mapping.Klient"%>
<%@page import="java.util.List"%>
<%@page import="mbud.hibernate.dao.KlientDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width">
		<link rel="stylesheet" type="text/css" href="../css/style.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<title>Wycena pogrzebu - Panel pracownika</title>
	</head>
	<body>
		<header class="panelHeader">
			<a href="index.html"><h1 class="panelHeaderTitle">Firma pogrzebowa</h1></a>
			<h2 class="headerDesc">Panel pracownika</h2>
		</header>
		<nav class="pageMenu">
			<c:if test="${sessionScope.userData.isLoggedIn()}">
				<p class="loggedAs">Zalogowano jako ${sessionScope.userData.imie} ${sessionScope.userData.nazwisko}</p>
			</c:if>
			<hr/>
			<p class="menuTitle">Nawigacja</p>
			<li class="navMenu">
				<ul class="navElement">
					<a href="index.html">Strona główna</a>
				</ul>
				<ul class="navElement">
					<a href="funerals.html">Pogrzeby</a>
				</ul>
				<ul class="navElement">
					<a href="orders.html">Zamówienia</a>
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
					<c:if test="${not empty sessionScope.cart}">
						<a id="cartLink" href="cart.html">Koszyk (${sessionScope.cart.numberOfItems})</a>
					</c:if>
					<c:if test="${empty sessionScope.cart}">
						<a id="cartLink" href="cart.html">Koszyk (0)</a>
					</c:if>
				</ul>
				<ul class="navElement">
					<a href="addNewClient.html">Dodaj klienta</a>
				</ul>
			</li>
			<hr/>
			<p class="menuTitle">Konto pracownika</p>
			<li class="navMenu">
				<ul class="navElement">
					<a href="../logout.html">Wyloguj się</a>
				</ul>
				<ul class="navElement">
					<a href="../index.html">Panel użytkownika</a>
				</ul>
			</li>
		</nav>
		<section class="pageContent">
			<h2>Wycena pogrzebu</h2>
			<p>Oszacuj koszt pogrzebu na podstawie poniższych danych:</p>
			<p><b>Adres denata:</b> ${pogrzeb.adresDenata}</p>
			<p><b>Kościół:</b> ${pogrzeb.kosciol.nazwa} (${pogrzeb.kosciol.adres})</p>
			<p><b>Cmentarz:</b> ${pogrzeb.cmentarz.nazwa} (${pogrzeb.cmentarz.adres})</p>
			<c:if test="${not empty pogrzeb.trumna}">
				<p><b>Trumna:</b> ${pogrzeb.trumna.produkt.nazwa} (cena: ${pogrzeb.trumna.produkt.cena} zł)</p>
			</c:if>
			<c:if test="${empty pogrzeb.trumna}">
				<p><b>Trumna:</b> We własnym zakresie</p>
			</c:if>
			<p class="errorMsg">${errorMsg}</p>
			<form method="POST" action="valuateFuneral.html">
				<p><b>Koszt pogrzebu: </b><input type="text" name="cost" /></p>
				<input type="text" name="id" value="${id}" hidden />
				<input type="submit" value="Zatwierdź" />
			</form>
		</section>
		<footer class="pageFooter">Kopyrajt &copy; 2016 by Maciej Budrowski </footer>
	</body>
</html>