<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rooms — MeetReserve</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@500;600;700&family=Inter:wght@400;500;600;700&family=JetBrains+Mono:wght@500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>

    <c:set var="role" value="${sessionScope.userConnected.role}" />
    <c:choose>
        <c:when test="${role == 'ADMINISTRATOR'}"><c:set var="dashboardUrl" value="/dashboard/administrator" /></c:when>
        <c:when test="${role == 'MANAGER'}"><c:set var="dashboardUrl" value="/dashboard/manager" /></c:when>
        <c:otherwise><c:set var="dashboardUrl" value="/dashboard/employee" /></c:otherwise>
    </c:choose>

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
                    <a class="nav-item" href="${pageContext.request.contextPath}${dashboardUrl}">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="9" rx="1.5"/><rect x="14" y="3" width="7" height="5" rx="1.5"/><rect x="14" y="12" width="7" height="9" rx="1.5"/><rect x="3" y="16" width="7" height="5" rx="1.5"/></svg>
                        Dashboard
                    </a>
                    <c:if test="${role != 'MANAGER' && role != 'ADMINISTRATOR'}">
                        <a class="nav-item" href="${pageContext.request.contextPath}/reservations">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M3 10h18M8 2v4M16 2v4"/></svg>
                            My reservations
                        </a>
                    </c:if>
                    <a class="nav-item active" href="${pageContext.request.contextPath}/rooms">
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
                    <div class="foot-role">${role}</div>
                </div>
                <form action="${pageContext.request.contextPath}/logout" method="post" style="margin-left:auto;">
                    <button type="submit" class="logout-link" style="background:none;border:none;cursor:pointer;">Logout</button>
                </form>
            </div>
        </aside>

        <main class="main">

            <div class="page-head">
                <div class="eyebrow">Rooms</div>
                <h1 class="page-title">All rooms</h1>
                <p class="page-desc">View available rooms, their capacity, and their location.</p>
            </div>

            <c:if test="${not empty param.error}">
                <div class="alert alert-error">${param.error}</div>
            </c:if>

            <div class="admin-toolbar">
                <h3>${fn:length(rooms)} room(s)</h3>
                <input class="search-input" type="text" id="roomSearch" placeholder="Search a room..." onkeyup="filterRooms()">
            </div>

            <div class="card">
                <table id="roomsTable">
                    <thead>
                        <tr>
                        	<th>Name</th>
                        	<th>Capacity</th>
                        	<th>Location</th>
                        	<th>Status</th>
                        	<th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty rooms}">
                                <tr class="empty-row">
                                	<td colspan="5">No room for the moment.</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="room" items="${rooms}">
                                    <tr>
                                        <td><strong>${room.nameRoom}</strong></td>
                                        <td>${room.capacity} places</td>
                                        <td>${room.location}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${room.available}">
                                                    <span class="status-pill confirmed">Free</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="status-pill cancelled">Unavailable</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="row-actions">
                                            <a href="${pageContext.request.contextPath}/rooms?id=${room.id}" class="icon-btn" title="Details">→</a>
                                            <c:if test="${role != 'MANAGER' && role != 'ADMINISTRATOR'}">
                                                <a href="${pageContext.request.contextPath}/reservation/new?roomId=${room.id}" class="icon-btn" title="Reserved">+</a>
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

    <script>
        function filterRooms() {
            const query = document.getElementById('roomSearch').value.toLowerCase();
            const rows = document.querySelectorAll('#roomsTable tbody tr');
            rows.forEach(row => {
                row.style.display = row.textContent.toLowerCase().includes(query) ? '' : 'none';
            });
        }
    </script>

</body>
</html>
