<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MeetReserve — Dashboard</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@500;600;700&family=Inter:wght@400;500;600;700&family=JetBrains+Mono:wght@500&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin1.css">
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
        <a href="${pageContext.request.contextPath}/dashboard/employee" class="nav-item active">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          	<rect x="3" y="3" width="7" height="9" rx="1.5"/>
          	<rect x="14" y="3" width="7" height="5" rx="1.5"/><rect x="14" y="12" width="7" height="9" rx="1.5"/><rect x="3" y="16" width="7" height="5" rx="1.5"/></svg>
          Dashboard
        </a>
        <a href="${pageContext.request.contextPath}/rooms?available=true" class="nav-item">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="16" rx="2"/><path d="M3 10h18M9 20V10"/></svg>
          Available Rooms
        </a>
        <a href="${pageContext.request.contextPath}/calendar" class="nav-item">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4.5" width="18" height="16" rx="2"/><path d="M3 9.5h18M8 3v3M16 3v3"/></svg>
          Calendar
        </a>
        <a href="${pageContext.request.contextPath}/reservations" class="nav-item">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 4h16v16H4z"/><path d="M8 9h8M8 13h5"/></svg>
          My reservations
        </a>
      </div>
    </div>

    <div>
      <div class="nav-group-label">Account</div>
      <div class="nav">
        <a href="${pageContext.request.contextPath}/notifications" class="nav-item">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          	<path d="M18 8a6 6 0 10-12 0c0 7-3 9-3 9h18s-3-2-3-9"/>
          	<path d="M13.7 21a2 2 0 01-3.4 0"/>
          </svg>
          Notifications
        </a>
      </div>
    </div>

    <div class="sidebar-foot">
      <div class="avatar"><c:out value="${fn:substring(user.name, 0, 1)}${fn:substring(user.surname, 0, 1)}"/></div>
      <div>
        <div class="foot-name"><c:out value="${user.name}"/></div>
        <div class="foot-role">Employee</div>
      </div>
      <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
    </div>
  </aside>

  <main class="main">

    <div class="page-head">
      <div class="eyebrow">Good Morning <c:out value="${user.name}"/></div>
      <h1 class="page-title">Dahboard</h1>
      <p class="page-desc">Overview of room occupancy and your upcoming reservations./p>
    </div>

    <div class="grid-3">
      <div class="card stat-card">
        <div class="stat-label">Rooms available now</div>
        <div class="stat-value">${availableNow} / ${totalRooms}</div>
        <div class="stat-delta" style="color:var(--muted)">${occupiedNow} Unavailable<c:if test="${occupiedNow > 1}">s</c:if> actually</div>
      </div>
      <div class="card stat-card">
        <div class="stat-label">Your reservations this week</div>
        <div class="stat-value">${reservationsThisWeek}</div>
        <c:choose>
          <c:when test="${not empty nextReservation}">
            <div class="stat-delta" style="color:var(--muted)">
              Next : <c:out value="${nextReservation.startTime}"/>
              <fmt:formatDate value="${nextReservation.reservationDate}" pattern="'le' dd MMM"/>
            </div>
          </c:when>
          <c:otherwise>
            <div class="stat-delta" style="color:var(--muted)">No upcoming reservations</div>
          </c:otherwise>
        </c:choose>
      </div>
      <div class="card stat-card">
        <div class="stat-label">Average occupancy rate</div>
        <div class="stat-value">${occupancyRate}%</div>
        <div class="stat-delta">Today, from 8h–18h</div>
      </div>
    </div>

    <div class="timeline-card card">
      <div class="timeline-head">
        <h3>Room occupancy — today</h3>
        <div class="timeline-legend">
          <span><span class="legend-dot" style="background:var(--red)"></span>Unavailable</span>
          <span><span class="legend-dot" style="background:var(--amber)"></span>Your reservation</span>
          <span><span class="legend-dot" style="background:var(--panel-alt); border:1px solid var(--line)"></span>Free</span>
        </div>
      </div>

      <c:forEach var="rt" items="${timelines}">
        <div class="room-row">
          <div>
            <div class="room-name"><c:out value="${rt.roomName}"/></div>
            <div class="room-cap">${rt.capacity} persons.</div>
          </div>
          <div class="track">
            <c:forEach var="slot" items="${rt.slots}">
              <div class="slot ${slot.cssClass}" style="left:${slot.left}%; width:${slot.width}%;"></div>
            </c:forEach>
          </div>
        </div>
      </c:forEach>

      <div class="track-hours"><div class="hours-row">
      	<span>8h</span>
      	<span>10h</span>
      	<span>12h</span>
      	<span>14h</span>
      	<span>16h</span>
      	<span>18h</span>
      </div>
     </div>
    </div>

  </main>
</div>

</body>
</html>
