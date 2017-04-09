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
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script src="js/pay.js"></script>
		<title>Płatność on-line - Firma pogrzebowa</title>
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
			<h2>Płatność on-line</h2>
			<p>Tutaj umieszczony byłby formularz płatności on-line.</p>
			<p>Kwota do zapłaty: ${kwota}</p>
			<form method="POST" action="payOnline.html">
				<input type="text" name="id" value="${id}" hidden />
				<input type="text" name="type" value="${type}" hidden />
				<input type="text" name="accept" value="1" hidden />
				<input type="submit" value="Zapłać" />
			</form>
		</section>
		<footer class="pageFooter">Kopyrajt &copy; 2016 by Maciej Budrowski </footer>
	</body>
</html>