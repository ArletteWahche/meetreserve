<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${room.nameRoom} — MeetReserve</title>
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
                <div class="eyebrow"><a href="${pageContext.request.contextPath}/rooms" style="color:var(--amber);">← Back to Rooms</a></div>
                <h1 class="page-title">${room.nameRoom}</h1>
                <p class="page-desc">${room.description}</p>
            </div>

            <div class="grid-3">
                <div class="card stat-card">
                    <div class="stat-label">Capacity</div>
                    <div class="stat-value">${room.capacity}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Location</div>
                    <div class="stat-value" style="font-size:18px;">${room.location}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Status</div>
                    <div style="margin-top:8px;">
                        <c:choose>
                            <c:when test="${room.available}">
                                <span class="status-pill confirmed">Free</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-pill cancelled">Unavailable</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <c:if test="${role != 'MANAGER' && role != 'ADMINISTRATOR' && room.available}">
                <div class="card" style="padding: 22px; margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/reservation/new?roomId=${room.id}" class="btn btn-amber">
                        + Reserve this room
                    </a>
                </div>
            </c:if>

        </main>
    </div>

</body>
</html>
