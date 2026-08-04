<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Dashboard</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
	<div class="layout">
		<aside class="sidebar">
			<div class="sidebar-header">
				<img src="${pageContext.request.contextPath}/images/Log1.png" alt="Logo" class="logo">
				
				<h2>MeetReserve</h2>
			</div>
			
			<nav class="sidebar-nav">
				<a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
				
				<a href="${pageContext.request.contextPath}/dashboard">Rooms</a>
				
				<a href="${pageContext.request.contextPath}/dashboard">Reservations</a>
				
				<a href="${pageContext.request.contextPath}/dashboard">Logout</a>
			</nav>
		</aside>
		
		<div class="main-area">
		
			<header class="topbar">
			
				<div class="topbar-left">
					<h2>Dashboard</h2>
				</div>
				
				<div class="topbar-right">
					<span class="user-name">${user.name} ${user.surname} </span>
				</div>
			
			</header>
			
			<main class="content">
				<section class="banner">
					<h1>Welcome ${user.name} ${user.surname}</h1>
				</section>
			
				<div class="cards">
					
					<div class="card">
						<h3>My reservations</h3>
						<p>${reservations.size()}</p>
					</div>
					
					<div class="card">
						<h3>Available rooms</h3>
						<p>${rooms.size()}</p>
					</div>
					
					<div class="card">
						<h3>Pending</h3>
						<p class="card-number">0</p>
					</div>
					
					<div class="card">
						<h3>Confirmed</h3>
						<p class="card-number">0</p>
					</div>
					
				</div>
				
				<section class="table-section">
				    <h2>My recent reservations</h2>
				
				    <c:if test="${empty reservations}">
				        <p>No reservation found.</p>
				    </c:if>
				
				    <c:if test="${not empty reservations}">
				        <table class="data-table">
				            <thead>
				            <tr>
				                <th>Date</th>
				                <th>Début</th>
				                <th>Fin</th>
				                <th>Sujet</th>
				                <th>Statut</th>
				            </tr>
				            </thead>
				            <tbody>
				            <c:forEach var="r" items="${reservations}">
				                <tr>
				                    <td>${r.reservationDate}</td>
				                    <td>${r.startTime}</td>
				                    <td>${r.endTime}</td>
				                    <td>${r.subject}</td>
				                    <td>${r.status}</td>
				                </tr>
				            </c:forEach>
				            </tbody>
				        </table>
				    </c:if>
				</section>
							
		</main>
			
		</div>
		
	</div>
</body>
</html>