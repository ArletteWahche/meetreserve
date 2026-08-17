<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard — MeetReserve</title>
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
                    <div class="brand-sub">Room Reservation</div>
                </div>
            </div>

            <div>
                <div class="nav-group-label">General</div>
                <div class="nav">
                    <a class="nav-item active" href="${pageContext.request.contextPath}/dashboard/employee">
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
                    
                    <a class="nav-item" href="${pageContext.request.contextPath}/historique">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3 3"/></svg>
                        History
                    </a>
                    <a class="nav-item" href="${pageContext.request.contextPath}/notifications">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M18 8a6 6 0 00-12 0c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.7 21a2 2 0 01-3.4 0"/></svg>
                        Notifications
                    </a>
                </div>
            </div>

            <div class="sidebar-foot">
                <div class="avatar">
                    ${fn:substring(user.name, 0, 1)}${fn:substring(user.surname, 0, 1)}
                </div>
                <div>
                    <div class="foot-name">${user.name} ${user.surname}</div>
                    <div class="foot-role">Employee</div>
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
                <div class="eyebrow">Good Morning ${user.name}</div>
                <h1 class="page-title">Dashboard</h1>
                <p class="page-desc">Overview of room occupancy and your upcoming reservations.</p>
            </div>
            

            <div class="grid-3">
                <div class="card stat-card">
                    <div class="stat-label">Rooms available now</div>
                    <div class="stat-value">${roomsAvailableNow} / ${totalRooms}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Your reservation of this week</div>
                    <div class="stat-value">${reservationsThisWeek}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">My total reservation</div>
                    <div class="stat-value">${fn:length(reservations)}</div>
                </div>
            </div>

            <div class="timeline-card card">
                <div class="timeline-head">
                    <h3>Room occupancy today</h3>
                    <div class="timeline-legend">
                        <span><span class="legend-dot" style="background:var(--red)"></span>Occupied</span>
                        <span><span class="legend-dot" style="background:var(--amber)"></span>Your reservation</span>
                        <span><span class="legend-dot" style="background:var(--panel-alt); border:1px solid var(--line)"></span>Free</span>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${empty occupancyRows}">
                        <p class="empty-message">No room registered for the moment.</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="row" items="${occupancyRows}">
                            <div class="room-row">
                                <div>
                                    <div class="room-name">${row.name}</div>
                                    <div class="room-cap">${row.capacity} persons.</div>
                                </div>
                                <div class="track">
                                    <c:forEach var="slot" items="${row.slots}">
                                        <div class="slot ${slot.cssClass}" style="left:${slot.left}%; width:${slot.width}%;"></div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="track-hours">
                            <div class="hours-row">
                            	<span>8h</span>
                            	<span>10h</span>
                            	<span>12h</span>
                            	<span>14h</span>
                            	<span>16h</span>
                            	<span>18h</span>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

        </main>
    </div>

</body>
</html>
