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
		<title>Pogrzeby - Panel pracownika</title>
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
			<h2>Niezrealizowane pogrzeby</h2>
			<c:if test="${empty niezrealizowanePogrzeby or niezrealizowanePogrzeby.size() eq 0 }">
				<p>Brak niezrealizowanych pogrzebów.</p>
				<form method="GET" action="addFuneral.html">
					<input type="submit" value="Dodaj nowy pogrzeb" />
				</form>
			</c:if>
			<c:if test="${not empty niezrealizowanePogrzeby and niezrealizowanePogrzeby.size() gt 0 }">
				<p>Niezrealizowane pogrzeby:</p>
				<form method="GET" action="addFuneral.html">
					<p><input type="submit" value="Dodaj nowy pogrzeb" /></p>
				</form>
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
						<c:forEach items="${niezrealizowanePogrzeby}" var="p">
							<tr>
								<td class="orderId">#${p.id}</td>
								<td class="orderBorderLeft">${p.imieDenata} ${p.nazwiskoDenata}</td>
								<td class="orderBorderLeft">${p.cenaString}</td>
								<td class="orderBorderLeft">${p.status}</td>
								<td class="orderBorderLeft">
									<button class="expand">Rozwiń</button>
									<c:if test="${p.canBeEdited()}">
										<form method="POST" action="editFuneral.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Edytuj" />
										</form>
										<form method="POST" action="deleteFuneral.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Usuń" />
										</form>
									</c:if>
									<c:if test="${empty p.koszt or p.koszt eq 0.0}">
										<form method="POST" action="valuateFuneral.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Wyceń pogrzeb" />
										</form>
									</c:if>
									<c:if test="${p.canDateBeDetermined()}">
										<form method="POST" action="determineDateFuneral.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Wyznacz termin" />
										</form>
									</c:if>
									<c:if test="${p.canBePaid()}">
										<form method="POST" action="receivePayment.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Przyjmij płatność" />
										</form>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="5">
									<div class="orderDetails" hidden> 
										<p><b>Klient:</b> ${p.klient.imie} ${p.klient.nazwisko}</p>
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
										<c:if test="${not empty p.koszt and p.koszt gt 0.0}">
											<p><b>Opłacony: </b>
												<c:if test="${p.oplacony}">TAK</c:if>
												<c:if test="${not p.oplacony}">NIE</c:if>
											</p>
										</c:if>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<h2>Zrealizowane pogrzeby</h2>
			<c:if test="${empty zrealizowanePogrzeby or zrealizowanePogrzeby.size() eq 0 }">
				<p>Brak zrealizowanych pogrzebów.</p>
			</c:if>
			<c:if test="${not empty zrealizowanePogrzeby and zrealizowanePogrzeby.size() gt 0 }">
				<p>Zrealizowane pogrzeby:</p>
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
						<c:forEach items="${zrealizowanePogrzeby}" var="p">
							<tr>
								<td class="orderId">#${p.id}</td>
								<td class="orderBorderLeft">${p.imieDenata} ${p.nazwiskoDenata}</td>
								<td class="orderBorderLeft">${p.cenaString}</td>
								<td class="orderBorderLeft">${p.status}</td>
								<td class="orderBorderLeft">
									<button class="expand">Rozwiń</button>
									<c:if test="${p.canBeEdited()}">
										<form method="POST" action="editFuneral.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Edytuj" />
										</form>
										<form method="POST" action="deleteFuneral.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Usuń" />
										</form>
									</c:if>
									<c:if test="${empty p.koszt or p.koszt eq 0.0}">
										<form method="POST" action="valuateFuneral.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Wyceń pogrzeb" />
										</form>
									</c:if>
									<c:if test="${p.canDateBeDetermined()}">
										<form method="POST" action="determineDateFuneral.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Wyznacz termin" />
										</form>
									</c:if>
									<c:if test="${p.canBePaid()}">
										<form method="POST" action="receivePayment.html">
											<input type="text" name="id" value="${p.id}" hidden />
											<input type="submit" value="Przyjmij płatność" />
										</form>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="5">
									<div class="orderDetails" hidden> 
										<p><b>Klient:</b> ${p.klient.imie} ${p.klient.nazwisko}</p>
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
										<p><b>Opłacony: </b>
											<c:if test="${p.oplacony}">TAK</c:if>
											<c:if test="${not p.oplacony}">NIE</c:if>
										</p>
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