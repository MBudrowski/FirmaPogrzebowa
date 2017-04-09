<%-- 
    Document   : index
    Created on : 2016-11-12, 17:53:02
    Author     : Maciek
--%>
<%@page import="mbud.hibernate.mapping.Klient"%>
<%@page import="java.util.List"%>
<%@page import="mbud.hibernate.dao.KlientDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width">
		<link rel="stylesheet" type="text/css" href="../css/style.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script src="../js/orders.js"></script>
		<title>Strona główna - Panel pracownika</title>
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
			<h2>Zamówienia do realizacji</h2>
			<c:if test="${empty zamowienia or zamowienia.size() eq 0 }">
				<p>Brak zamówień.</p>
			</c:if>
			<c:if test="${not empty zamowienia and zamowienia.size() gt 0 }">
				<table class="orders">
					<thead>
						<tr class="firstRow">
							<td>ID</td>
							<td class="orderBorderLeft">Koszt</td>
							<td class="orderBorderLeft">Status</td>
							<td class="orderBorderLeft">Akcje</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${zamowienia}" var="z">
							<tr>
								<td class="orderId">#${z.id}</td>
								<td class="orderBorderLeft">${z.totalPrice}</td>
								<td class="orderBorderLeft">${z.status}</td>
								<td class="orderBorderLeft">
									<button class="expand">Rozwiń</button>
									<c:if test="${z.canBeGiven()}">
										<form method="POST" action="realizeOrder.html">
											<input type="text" name="id" value="${z.id}" hidden />
											<input type="submit" value="Wydaj" />
										</form>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div class="orderDetails" hidden> 
										<p><b>Zawartość zamówienia: </b>
										<c:forEach items="${z.produkty}" var="p" varStatus="i">
											<c:if test="${i.index ne 0}">, </c:if>
											
											${p.id.produkt.nazwa} (${p.ilosc}x${p.id.produkt.cenaString} zł)
										</c:forEach>
										<c:if test="${not empty z.klient}">
											<p><b>Klient:</b> ${z.klient.imie} ${z.klient.nazwisko} (telefon: ${z.klient.telefon})</p>
										</c:if>
										<p><b>Data złożenia zamówienia:</b> ${z.dataZlozenia}</p>
										<p><b>Miejsce odbioru:</b> ${z.domPogrzebowy.adres}, telefon: ${z.domPogrzebowy.telefon}</p>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<h2>Ostatnie zrealizowane zamówienia</h2>
			<c:if test="${empty zamowieniaZr or zamowieniaZr.size() eq 0 }">
				<p>Brak zamówień.</p>
			</c:if>
			<c:if test="${not empty zamowieniaZr and zamowieniaZr.size() gt 0 }">
				<table class="orders">
					<thead>
						<tr class="firstRow">
							<td>ID</td>
							<td class="orderBorderLeft">Koszt</td>
							<td class="orderBorderLeft">Status</td>
							<td class="orderBorderLeft">Akcje</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${zamowieniaZr}" var="z">
							<tr>
								<td class="orderId">#${z.id}</td>
								<td class="orderBorderLeft">${z.totalPrice}</td>
								<td class="orderBorderLeft">${z.status}</td>
								<td class="orderBorderLeft">
									<button class="expand">Rozwiń</button>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div class="orderDetails" hidden> 
										<p><b>Zawartość zamówienia: </b>
										<c:forEach items="${z.produkty}" var="p" varStatus="i">
											<c:if test="${i.index ne 0}">, </c:if>
											
											${p.id.produkt.nazwa} (${p.ilosc}x${p.id.produkt.cenaString} zł)
										</c:forEach>
										<c:if test="${not empty z.klient}">
											<p><b>Klient:</b> ${z.klient.imie} ${z.klient.nazwisko} (telefon: ${z.klient.telefon})</p>
										</c:if>
										<c:if test="${not empty z.pracownik}">
											<p><b>Pracownik realizujący zamowienie:</b> ${z.pracownik.imie} ${z.pracownik.nazwisko}</p>
										</c:if>
										<p><b>Data złożenia zamówienia:</b> ${z.dataZlozenia}</p>
										<p><b>Miejsce odbioru:</b> ${z.domPogrzebowy.adres}, telefon: ${z.domPogrzebowy.telefon}</p>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</section>
		<footer class="pageFooter">Kopyrajt &copy; 2016 by Maciej Budrowski </footer>
	</body>
</html>