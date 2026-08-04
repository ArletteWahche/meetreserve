<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    Object user = session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Manager Dashbord</title>
	<link rel="stylesheet" href="<c:url value='/css/dashboard.css'/>">
</head>
<body>
	<a href="${pageContext.request.contextPath}/logout">Logout</a>
	
	<div class="layout">
		<aside class="sidebar">
			<div class="sidebar-header">
				<h2>Manager Panel</h2>
			</div>
			
			<nav class="sidebar-nav">
				<a href="#" >Home</a>
				<a href="#" >Demands</a>
				<a href="#" >Reservations</a>
				<a href="#" >Staff</a>
				<a href="<c:url value='/logout'/>">Logout</a>
			</nav>
		</aside>
	
	</div>
	<div class="main-area">
		
			<header class="topbar">
					<h2>Manager Dashbord</h2>
				
					<span class="user-name">Welcome, ${sessionScope.user.name}</span>
			</header>
			
			<main class="content">
				<section class="banner">
					<h1>Welcome on the manager dashbord</h1>
					
					<p>Track requests and approve reservations for your team.</p>
				</section>
			
				<div class="cards">
					
					<div class="card">
						<h3>Pending requests</h3>
						<p class="stat-number">9</p>
					</div>
					
					<div class="card">
						<h3>Approved reservations</h3>
						<p class="stat-number">30</p>
					</div>
					
					<div class="card">
						<h3>Active team</h3>
						<p class="stat-number">60</p>
					</div>
					
				</div>
				
				
							
			</main>
		
		</div>
		
		
</body>
</html>