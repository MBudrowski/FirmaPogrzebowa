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
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script src="js/orders.js"></script>
		<title>Zamówienia - Firma pogrzebowa</title>
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
			<h2>Zamówienia</h2>
			<c:if test="${empty zamowienia or zamowienia.size() eq 0 }">
				<p>Brak zamówień.</p>
			</c:if>
			<c:if test="${not empty zamowienia and zamowienia.size() gt 0 }">
				<p>Twoje zamówienia:</p>
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
									<c:if test="${z.canBePaid()}">
										<form method="POST" action="payOnline.html">
											<input type="text" name="id" value="${z.id}" hidden />
											<input type="text" name="type" value="z" hidden />
											<input type="submit" value="Opłać on-line" />
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
										<p><b>Data złożenia zamówienia:</b> ${z.dataZlozenia}</p>
										<p><b>Miejsce odbioru:</b> ${z.domPogrzebowy.adres}, telefon: ${z.domPogrzebowy.telefon}</p>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${empty pogrzeby or pogrzeby.size() eq 0 }">
				<p>Brak zamówionych pogrzebów.</p>
			</c:if>
			<c:if test="${not empty pogrzeby and pogrzeby.size() gt 0 }">
				<p>Twoje zamówione pogrzeby:</p>
				<table class="orders">
					<thead>
						<tr class="firstRow">
							<td>ID</td>
							<td class="orderBorderLeft">Imię i nazwisko denata</td>
							<td class="orderBorderLeft">Koszt</td>
							<td class="orderBorderLeft">Status</td>
							<td class="orderBorderLeft">Akcje</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pogrzeby}" var="p">
							<tr>
								<td class="orderId">#${p.id}</td>
								<td class="orderBorderLeft">${p.imieDenata} ${p.nazwiskoDenata}</td>
								<td class="orderBorderLeft">${p.cenaString}</td>
								<td class="orderBorderLeft">${p.status}</td>
								<td class="orderBorderLeft">
									<button class="expand">Rozwiń</button>
									<c:if test="${p.canBePaid()}">
										<form method="POST" action="payOnline.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="text" name="type" value="p" hidden />
											<input type="submit" value="Opłać on-line" />
										</form>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="5">
									<div class="orderDetails" hidden> 
										<p><b>Adres denata:</b> ${p.adresDenata}</p>
										<p><b>Data urodzenia:</b> ${p.dataUrodzenia}</p>
										<p><b>Data śmierci:</b> ${p.dataSmierci}</p> 
										<c:if test="${not empty p.dataWystawieniaCiala}">
											<p><b>Data wystawienia ciała:</b> ${p.dataWystawieniaCiala}</p>
										</c:if>
										<c:if test="${not empty p.dataPogrzebu}">
										<fmt:formatDate value="${p.dataPogrzebu}" var="formattedDate" 
							                type="date" pattern="yyyy-MM-dd HH:mm" />
											
											<p><b>Data pogrzebu:</b> ${formattedDate}</p>
										</c:if>
										<p><b>Data złożenia zamówienia:</b> ${p.dataZamowienia}</p>
										<p><b>Dom pogrzebowy:</b> ${p.domPogrzebowy.adres}</p>
										<p><b>Kościół:</b> ${p.kosciol.adres}</p>
										<p><b>Cmentarz:</b> ${p.cmentarz.adres}</p>
										<c:if test="${not empty p.trumna}">
											<p><b>Wybrana trumna:</b> ${p.trumna.produkt.nazwa}</p>
										</c:if>
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