<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MeetReserve — Calendar</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@500;600;700&family=Inter:wght@400;500;600;700&family=JetBrains+Mono:wght@500&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin1.css">
</head>
<body>

<div class="app">

  <aside class="sidebar">
    <div class="brand">
      <div class="brand-mark">K</div>
      <div><div class="brand-name">MeetReserve</div>
      <div class="brand-sub">Meeting Room Reservation</div></div>
    </div>
    <div>
      <div class="nav-group-label">General</div>
      <div class="nav">
        <a href="${pageContext.request.contextPath}/dashboard/employee" class="nav-item">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="9" rx="1.5"/><rect x="14" y="3" width="7" height="5" rx="1.5"/><rect x="14" y="12" width="7" height="9" rx="1.5"/><rect x="3" y="16" width="7" height="5" rx="1.5"/></svg>
          Dashboard
        </a>
        <a href="${pageContext.request.contextPath}/rooms?available=true" class="nav-item">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="16" rx="2"/><path d="M3 10h18M9 20V10"/></svg>
          Available rooms
        </a>
        <a href="${pageContext.request.contextPath}/calendar" class="nav-item active">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4.5" width="18" height="16" rx="2"/><path d="M3 9.5h18M8 3v3M16 3v3"/></svg>
          Calendar
        </a>
        <a href="${pageContext.request.contextPath}/reservations" class="nav-item">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 4h16v16H4z"/><path d="M8 9h8M8 13h5"/></svg>
          My reservations
        </a>
      </div>
    </div>
  </aside>

  <main class="main">

    <div class="page-head">
      <div class="eyebrow">View all</div>
      <h1 class="page-title">Calendar</h1>
      <p class="page-desc">Occupancy of all rooms this week.</p>
    </div>

    <div class="cal-toolbar">
      <div class="cal-nav">‹ &nbsp; ${weekLabel} &nbsp; ›</div>
      <a href="${pageContext.request.contextPath}/rooms?available=true" class="btn btn-amber">+ New reservation</a>
    </div>

    <div class="cal-grid">
      <div class="cal-time" style="background:var(--panel-alt); border-bottom:1px solid var(--line);"></div>
      <c:forEach var="dayLabel" items="${weekDayLabels}">
        <div class="cal-head"><c:out value="${dayLabel}"/></div>
      </c:forEach>

      <c:forEach var="row" items="${rows}">
        <div class="cal-time">${row.label}</div>
        <c:forEach var="cell" items="${row.cells}">
          <div class="cal-cell">
            <c:if test="${not empty cell}">
              <div class="cal-evt ${cell.userId != currentUserId ? 'other' : ''}">
                <c:out value="${cell.roomName}"/> · <c:out value="${cell.subject}"/>
              </div>
            </c:if>
          </div>
        </c:forEach>
      </c:forEach>
    </div>

  </main>
</div>

</body>
</html>
