<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>History — MeetReserve</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@500;600;700&family=Inter:wght@400;500;600;700&family=JetBrains+Mono:wght@500&display=swap" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/sidebar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>

    <div class="app">
		<div class="sidebar-overlay" id="sidebarOverlay"></div>
        <aside class="sidebar" id="sidebar">
            <div class="brand">
                <div class="brand-mark">MR</div>
                <div>
                    <div class="brand-name">MeetReserve</div>
                    <div class="brand-sub">Room Reservation</div>
                </div>
            </div>

            <div>
                <div class="nav-group-label">General</div>
                <div class="nav">
                    <a class="nav-item" href="${pageContext.request.contextPath}/dashboard/employee">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="9" rx="1.5"/><rect x="14" y="3" width="7" height="5" rx="1.5"/><rect x="14" y="12" width="7" height="9" rx="1.5"/><rect x="3" y="16" width="7" height="5" rx="1.5"/></svg>
                        Dashboard
                    </a>
                    <a class="nav-item" href="${pageContext.request.contextPath}/rooms">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="16" rx="2"/><path d="M3 10h18M9 20V10"/></svg>
                        Available rooms
                    </a>
                    <a class="nav-item" href="${pageContext.request.contextPath}/calendar">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4.5" width="18" height="16" rx="2"/><path d="M3 9.5h18M8 3v3M16 3v3"/></svg>
                        Calendar
                    </a>
                    <a class="nav-item" href="${pageContext.request.contextPath}/reservations">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 4h16v16H4z"/><path d="M8 9h8M8 13h5"/></svg>
                        My reservations
                    </a>
                </div>
            </div>

            <div>
                <div class="nav-group-label">Account</div>
                <div class="nav">
                    <a class="nav-item active" href="${pageContext.request.contextPath}/history">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3 3"/></svg>
                        History
                    </a>
                    <a class="nav-item" href="${pageContext.request.contextPath}/notification">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M18 8a6 6 0 00-12 0c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.7 21a2 2 0 01-3.4 0"/></svg>
                        Notifications
                        <c:if test="${unreadCount > 0}"><span class="notif-badge">${unreadCount}</span></c:if>
                    </a>
                </div>
            </div>

            <div class="sidebar-foot">
                <div class="avatar">${fn:substring(user.name, 0, 1)}${fn:substring(user.surname, 0, 1)}</div>
                <div>
                    <div class="foot-name">${user.name} ${user.surname}</div>
                    <div class="foot-role">Employee</div>
                </div>
            </div>
        </aside>

        <main class="main">

            <div class="top-bar">
			    <button type="button" class="hamburger-btn" id="sidebarToggle" aria-label="Menu">
			        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 6h18M3 12h18M3 18h18"/></svg>
			    </button>
			    <form action="${pageContext.request.contextPath}/logout" method="post" style="margin-left:auto;">
			        <button type="submit" class="btn-logout-top">Logout</button>
			    </form>
			</div>

            <div class="page-head">
                <div class="eyebrow">History</div>
                <h1 class="page-title">Past reservations</h1>
                <p class="page-desc">Reservations that already took place, or that were cancelled.</p>
            </div>

            <div class="card">
                <table>
                    <thead>
                        <tr>
                        	<th>Room</th>
                        	<th>Date</th>
                        	<th>Schedule</th>
                        	<th>Object</th>
                        	<th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty reservations}">
                                <tr class="empty-row"><td colspan="5">No history for the moment.</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="r" items="${reservations}">
                                    <tr>
                                        <td><strong>${r.subject}</strong></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty r.roomName}">${r.roomName}</c:when>
                                                <c:otherwise>Room #${r.roomId}</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${r.reservationDate}" pattern="dd/MM/yyyy" /></td>
                                        <td><fmt:formatDate value="${r.startTime}" pattern="HH:mm" /> – <fmt:formatDate value="${r.endTime}" pattern="HH:mm" /></td>
                                        <td><span class="status-pill ${r.status}">${r.status}</span></td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

        </main>
    </div>

</body>
</html>