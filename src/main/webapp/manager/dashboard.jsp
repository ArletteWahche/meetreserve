<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard — MeetReserve</title>
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
                    <div class="brand-sub">Manager Dashboard</div>
                </div>
            </div>

            <div>
                <div class="nav-group-label">General</div>
                <div class="nav">
                    <a class="nav-item active" href="${pageContext.request.contextPath}/dashboard/manager">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="9" rx="1.5"/><rect x="14" y="3" width="7" height="5" rx="1.5"/><rect x="14" y="12" width="7" height="9" rx="1.5"/><rect x="3" y="16" width="7" height="5" rx="1.5"/></svg>
                        Dashboard
                    </a>
                    <a class="nav-item" href="${pageContext.request.contextPath}/rooms">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="16" rx="2"/><path d="M3 10h18M9 20V10"/></svg>
                        Rooms
                    </a>
                </div>
            </div>

            <div class="sidebar-foot">
                <div class="avatar">
                    ${fn:substring(currentUser.name, 0, 1)}${fn:substring(currentUser.surname, 0, 1)}
                </div>
                <div>
                    <div class="foot-name">${currentUser.name} ${currentUser.surname}</div>
                    <div class="foot-role">Manager</div>
                </div>
            </div>
        </aside>

        <main class="main">
			
				<div class="top-bar">
	                <form action="${pageContext.request.contextPath}/logout" method="post" style="margin-left:auto;">
	                    <button type="submit" class="btn-logout-top" >Logout</button>
	                </form>
            	</div>
			
            <div class="page-head">
                <div class="eyebrow">Good Morning ${currentUser.name}</div>
                <h1 class="page-title">Team Reservations</h1>
                <p class="page-desc">View all meeting room reservations and approve or cancel pending requests.</p>
            </div>

            <div class="grid-3">
                <div class="card stat-card">
                    <div class="stat-label">Total reservations</div>
                    <div class="stat-value">${fn:length(reservations)}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Pending Approval ✅</div>
                    <div class="stat-value">${pendingCount}</div>
                    <c:if test="${pendingCount > 0}">
                        <div class="stat-delta" style="color:var(--amber)">Required action</div>
                    </c:if>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Total rooms</div>
                    <div class="stat-value">${fn:length(rooms)}</div>
                </div>
            </div>

            <div class="admin-toolbar">
                <h3>All reservations</h3>
                <input class="search-input" type="text" id="resSearch" placeholder="Rechercher..." onkeyup="filterReservations()">
            </div>

            <div class="card">
                <table id="resTable">
                    <thead>
                        <tr>
                        	<th>Room</th>
                        	<th>Date</th>
                        	<th>Schedule</th>
                        	<th>Object</th>
                        	<th>Status</th
                        	><th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty reservations}">
                                <tr class="empty-row"><td colspan="6">No reservations for the moment.</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="r" items="${reservations}">
                                    <tr>
                                        <td><strong>${roomNames[r.roomId]}</strong></td>
                                        <td><fmt:formatDate value="${r.reservationDate}" pattern="dd/MM/yyyy" /></td>
                                        <td><fmt:formatDate value="${r.startTime}" pattern="HH:mm" /> – <fmt:formatDate value="${r.endTime}" pattern="HH:mm" /></td>
                                        <td>${r.subject}</td>
                                        <td><span class="status-pill ${r.status}">${r.status}</span></td>
                                        <td class="row-actions">
                                            <c:if test="${r.status == 'PENDING'}">
                                                <form action="${pageContext.request.contextPath}/dashboard/manager" method="post" style="display:inline;">
                                                    <input type="hidden" name="id" value="${r.id}">
                                                    <input type="hidden" name="action" value="confirm">
                                                    <button type="submit" class="icon-btn" title="Confirmer">✓</button>
                                                </form>
                                            </c:if>
                                            <c:if test="${r.status != 'CANCELLED'}">
                                                <form action="${pageContext.request.contextPath}/dashboard/manager" method="post" style="display:inline;">
                                                    <input type="hidden" name="id" value="${r.id}">
                                                    <input type="hidden" name="action" value="cancel">
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

    <script>
        function filterReservations() {
            const query = document.getElementById('resSearch').value.toLowerCase();
            const rows = document.querySelectorAll('#resTable tbody tr');
            rows.forEach(row => {
                row.style.display = row.textContent.toLowerCase().includes(query) ? '' : 'none';
            });
        }
    </script>

</body>
</html>
