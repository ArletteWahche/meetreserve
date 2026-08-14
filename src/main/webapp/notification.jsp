<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MeetReserve — Notifications</title>
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
        <a href="${pageContext.request.contextPath}/notifications" class="nav-item active">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M18 8a6 6 0 10-12 0c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.7 21a2 2 0 01-3.4 0"/></svg>
          Notifications
        </a>
      </div>
    </div>
  </aside>

  <main class="main">

    <div class="page-head">
      <div class="eyebrow">Activity</div>
      <h1 class="page-title">Notifications</h1>
      <p class="page-desc">Stay informed about confirmations, reminders, and changes to your reservations.</p>
    </div>

    <div class="card" style="overflow:hidden;">
      <c:choose>
        <c:when test="${not empty notifications}">
          <c:forEach var="n" items="${notifications}">
            <div class="notif-item ${n.unread ? 'unread' : ''}">
              <div class="notif-icon ${n.type}">
                <c:choose>
                  <c:when test="${n.type == 'confirm'}">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M5 13l4 4L19 7"/></svg>
                  </c:when>
                  <c:when test="${n.type == 'reminder'}">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3 3"/></svg>
                  </c:when>
                  <c:otherwise>
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M6 6l12 12M18 6L6 18"/></svg>
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="notif-text">
                <div class="notif-title"><c:out value="${n.title}"/></div>
                <div><c:out value="${n.text}"/></div>
                <div class="notif-time"><c:out value="${n.timestamp}"/></div>
              </div>
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div class="empty-state">
            <div class="empty-state-title">No notification</div>
            <div class="empty-state-desc">You are up to date !</div>
          </div>
        </c:otherwise>
      </c:choose>
    </div>

  </main>
</div>

</body>
</html>
