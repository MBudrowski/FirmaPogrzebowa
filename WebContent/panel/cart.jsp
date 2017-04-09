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
		<script src="../js/cart.js"></script>
		<title>Koszyk - Panel pracownika</title>
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
			<h2>Koszyk</h2>
			<c:if test="${not empty sessionScope.cart and sessionScope.cart.numberOfItems gt 0}">
				<p>Zawartość koszyka:</p>
				<table class="products">
					<thead>
						<tr>
							<td>Zdjęcie</td>
							<td class="productInfo">Nazwa produktu</td>
							<td class="productInfo">Cena (sztuka)</td>
							<td class="productInfo">Ilość</td>
							<td class="productInfo">Łączna cena</td>
							<td class="productInfo">Akcje</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${sessionScope.cart.products}" var="p">
							<tr data-product-id="${p.key.id}">
								<td class="productImg"><img src="../${p.key.sciezkaObrazka}" class="productImg" /></td>
								<td class="productInfo">${p.key.nazwa}</td>
								<td class="productInfo">${p.key.cenaString} zł</td>
								<td class="productInfo countCell">${p.value}</td>
								<td class="productInfo">${p.key.getCenaLaczna(p.value)} zł</td>
								<td class="productInfo">
									<p class="errorMsg" hidden></p>
									<button class="editCount">Edytuj</button>
									<button class="accept" hidden>Zatwierdź</button>
									<button class="cancel" hidden>Anuluj</button>
									<button class="delete">Usuń</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4" class="totalPrice">Łączna wartość zamówienia:</td>
							<td class="productInfo">${sessionScope.cart.totalPrice}</td>
							<td class="productInfo"></td>
						</tr>
					</tfoot>
				</table>
				<h3>Złóż zamówienie:</h3>
				<form method="POST" action="PanelOrderServlet">
					<table>
						<tr>
							<td>Klient:</td>
							<td>
								<select name="klient">
									<option value="0">Brak</option>
									<c:forEach items="${klienci}" var="k">
										<option value="${k.id}">${k.imie} ${k.nazwisko}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="submit" value="Złóż zamówienie" id="button-1"/>
							</td>
						</tr>
					</table>
				</form>
			</c:if>
			<c:if test="${empty sessionScope.cart or sessionScope.cart.numberOfItems eq 0}">
				<p>Koszyk jest pusty.</p>
			</c:if>
        </section>
		<footer class="pageFooter">Kopyrajt &copy; 2016 by Maciej Budrowski </footer>
	</body>
</html>