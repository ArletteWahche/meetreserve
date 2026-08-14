<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My reservations — MeetReserve</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@500;600;700&family=Inter:wght@400;500;600;700&family=JetBrains+Mono:wght@500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>

    <div class="app">

        <aside class="sidebar">
            <div class="brand">
                <div class="brand-mark">MR</div>
                <div>
                    <div class="brand-name">MeetReserve</div>
                    <div class="brand-sub">Meeting Room Reservation</div>
                </div>
            </div>

            <div>
                <div class="nav-group-label">General</div>
                <div class="nav">
                    <a class="nav-item" href="${pageContext.request.contextPath}/dashboard/employee">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="9" rx="1.5"/><rect x="14" y="3" width="7" height="5" rx="1.5"/><rect x="14" y="12" width="7" height="9" rx="1.5"/><rect x="3" y="16" width="7" height="5" rx="1.5"/></svg>
                        Dashboard
                    </a>
                    <a class="nav-item active" href="${pageContext.request.contextPath}/reservations">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M3 10h18M8 2v4M16 2v4"/></svg>
                        My reservations
                    </a>
                    <a class="nav-item" href="${pageContext.request.contextPath}/rooms">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="16" rx="2"/><path d="M3 10h18M9 20V10"/></svg>
                        Rooms
                    </a>
                </div>
            </div>

            <div class="sidebar-foot">
                <div class="avatar">
                    ${fn:substring(sessionScope.userConnected.name, 0, 1)}${fn:substring(sessionScope.userConnected.surname, 0, 1)}
                </div>
                <div>
                    <div class="foot-name">${sessionScope.userConnected.name} ${sessionScope.userConnected.surname}</div>
                    <div class="foot-role">Employee</div>
                </div>
                <form action="${pageContext.request.contextPath}/logout" method="post" style="margin-left:auto;">
                    <button type="submit" class="logout-link" style="background:none;border:none;cursor:pointer;">Logout</button>
                </form>
            </div>
        </aside>

        <main class="main">

            <div class="page-head">
                <div class="eyebrow">Reservations</div>
                <h1 class="page-title">My reservations</h1>
                <p class="page-desc">View all your room reservations here</p>
            </div>

            <div class="admin-toolbar">
                <h3>${fn:length(reservations)} reservation(s)</h3>
                <a href="${pageContext.request.contextPath}/reservation/new" class="btn btn-amber">+ New reservation</a>
            </div>

            <div class="card">
                <table>
                    <thead>
                        <tr>
                        	<th>Object</th>
                        	<th>Room</th>
                        	<th>Date</th>
                        	<th>Schedule</th>
                        	<th>Status</th>
                        	<th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty reservations}">
                                <tr class="empty-row"><td colspan="6">You don’t have any reservations yet.</td></tr>
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
                                        <td><fmt:formatDate value="${r.startTime}" pattern="HH:mm" />–<fmt:formatDate value="${r.endTime}" pattern="HH:mm" /></td>
                                        <td><span class="status-pill ${r.status}">${r.status}</span></td>
                                        <td>
                                            <c:if test="${r.status != 'CANCELLED'}">
                                                <form action="${pageContext.request.contextPath}/reservations" method="post" style="display:inline;">
                                                    <input type="hidden" name="action" value="cancel">
                                                    <input type="hidden" name="id" value="${r.id}">
                                                    <button type="submit" class="icon-btn" title="Annuler"
                                                            onclick="return confirm('Cancel this reservation ?');">✕</button>
                                                </form>
                                            </c:if>
                                        </td>
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
