<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard — MeetReserve</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

    <div class="layout">

        <!-- ===================== SIDEBAR ===================== -->
        <aside class="sidebar">
            <div class="sidebar-logo">
                <div class="logo-container">
                    <img src="${pageContext.request.contextPath}/images/Log2.png" alt="Logo" class="logo">
                </div>
                <div class="logo-text">
                    <h2>MeetReserve</h2>
                    <p>Meeting Room Reservation</p>
                </div>
            </div>

            <p class="sidebar-section-label">My account</p>

            <nav class="sidebar-nav">
                <a href="${pageContext.request.contextPath}/dashbord/employee" class="nav-item active">
                    <i class="fa-solid fa-house"></i> Home
                </a>
                <a href="${pageContext.request.contextPath}/reservations" class="nav-item">
                    <i class="fa-regular fa-calendar"></i> My reservations
                </a>
                <a href="${pageContext.request.contextPath}/assignments" class="nav-item">
                    <i class="fa-solid fa-list-check"></i> My Assigned Rooms
                </a>
                <a href="${pageContext.request.contextPath}/rooms" class="nav-item">
                    <i class="fa-solid fa-table-cells-large"></i> Available rooms
                </a>
                <a href="${pageContext.request.contextPath}/history" class="nav-item">
                    <i class="fa-regular fa-clock"></i> History
                </a>
            </nav>
        </aside>

        <!-- ===================== MAIN ===================== -->
        <main class="main-content">

            <!-- Barre du haut -->
            <header class="topbar">
                <div class="topbar-title">
                    <h1>My Dashboard</h1>
                    <p>Welcome to your MeetReserve account</p>
                </div>

                <div class="topbar-actions">
                    <div class="search-box">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        <input type="text" placeholder="Rechercher..." />
                    </div>

                    <button class="icon-btn" type="button" aria-label="Notifications">
                        <i class="fa-regular fa-bell"></i>
                        <c:if test="${not empty notificationCount && notificationCount > 0}">
                            <span class="badge-count">${notificationCount}</span>
                        </c:if>
                    </button>

                    <form action="${pageContext.request.contextPath}/logout" method="post" class="logout-form">
                        <button type="submit" class="btn-logout">Logout</button>
                    </form>
                </div>
            </header>

            <!-- Bannière de bienvenue -->
            <section class="welcome-banner">
                <div>
                    <h2>Good Morning, ${user.name} <span class="wave">👋</span></h2>
                    <p>You have ${activeReservationsCount} active reservations this week. ✅</p>
                </div>
                <a href="${pageContext.request.contextPath}/reservation/new" class="btn-primary">
                    <i class="fa-solid fa-plus"></i> New reservation
                </a>
            </section>

            <!-- Cartes statistiques -->
            <section class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon icon-gray"><i class="fa-regular fa-calendar"></i></div>
                    <p class="stat-label">Reserved rooms</p>
                    <p class="stat-value">${reservedRoomsCount}</p>
                </div>
                <div class="stat-card">
                    <div class="stat-icon icon-orange"><i class="fa-regular fa-clock"></i></div>
                    <p class="stat-label">Pending requests</p>
                    <p class="stat-value">${pendingCount}</p>
                </div>
                <div class="stat-card">
                    <div class="stat-icon icon-green"><i class="fa-solid fa-check"></i></div>
                    <p class="stat-label">✅ Completed Reservations ⭐</p>
                    <p class="stat-value">${completedCount}</p>
                </div>
            </section>

            <!-- Colonnes : réservations + salles -->
            <section class="columns-grid">

                <div class="panel">
                    <div class="panel-header">
                        <h3>My reservations</h3>
                        <a href="${pageContext.request.contextPath}/reservations" class="link-see-all">View all</a>
                    </div>

                    <c:choose>
                        <c:when test="${empty reservations}">
                            <p class="empty-message">You have no reservation for the moment.</p>
                        </c:when>
                        <c:otherwise>
                            <ul class="list">
                                <c:forEach var="reservation" items="${reservations}">
                                    <li class="list-item">
                                        <div class="list-item-icon"><i class="fa-regular fa-calendar"></i></div>
                                        <div class="list-item-info">
                                            <p class="item-title">${reservation.subject}</p>
                                            <p class="item-subtitle">
                                                Reserved : <fmt:formatDate value="${reservation.reservationDate}" pattern="dd/MM/yyyy" /> ·
                                                <fmt:formatDate value="${reservation.startTime}" pattern="HH:mm" />–<fmt:formatDate value="${reservation.endTime}" pattern="HH:mm" />
                                            </p>
                                        </div>
                                        <span class="status status-${reservation.status}">
                                            ${reservation.status}
                                        </span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="panel">
                    <div class="panel-header">
                        <h3>Available rooms</h3>
                        <a href="${pageContext.request.contextPath}/reservation/new" class="link-see-all">New</a>
                    </div>

                    <c:choose>
                        <c:when test="${empty rooms}">
                            <p class="empty-message">No room available actually.</p>
                        </c:when>
                        <c:otherwise>
                            <ul class="list">
                                <c:forEach var="room" items="${rooms}">
                                    <li class="list-item">
                                        <div class="list-item-icon"><i class="fa-solid fa-table-cells-large"></i></div>
                                        <div class="list-item-info">
                                            <p class="item-title">${room.nameRoom} · ${room.capacity} places</p>
                                            <p class="item-subtitle">${room.location}</p>
                                        </div>
                                        <span class="badge-free">Free</span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>

            </section>

        </main>
    </div>

</body>
</html>
