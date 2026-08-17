<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New reservation — MeetReserve</title>
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
                    <a class="nav-item" href="${pageContext.request.contextPath}/rooms">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="16" rx="2"/><path d="M3 10h18M9 20V10"/></svg>
                        Available rooms
                    </a>
                    <a class="nav-item active" href="${pageContext.request.contextPath}/calendar">
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
        </aside>

        <main class="main" style="max-width: 640px;">
        
        	<div class="top-bar">
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit" class="btn-logout-top">Logout</button>
                </form>
            </div>
        	

            <div class="page-head">
                <div class="eyebrow">New reservation</div>
                <h1 class="page-title">Book a room</h1>
                <p class="page-desc">Choose a room, a date, and a time slot - your request will remain pending until it is approved.</p>
            </div>

            <c:choose>
                <c:when test="${param.error == 'conflict'}">
                    <div class="alert alert-error">This room is already booked for this time slot, Please choose another time or another room.</div>
                </c:when>
                <c:when test="${param.error == 'invalid_data'}">
                    <div class="alert alert-error">Please fill in all fields correctly.</div>
                </c:when>
                <c:when test="${param.error == 'creation_failed'}">
                    <div class="alert alert-error">An error occurred, Please try again.</div>
                </c:when>
            </c:choose>

            <div class="card" style="padding: 24px;">
                <form action="${pageContext.request.contextPath}/reservations" method="post">

                    <div class="field">
                        <label for="roomId">Room</label>
                        <select id="roomId" name="roomId" required>
                            <option value="" disabled ${empty param.roomId ? 'selected' : ''}>Choose a room</option>
                            <c:forEach var="room" items="${rooms}">
                                <option value="${room.id}" ${param.roomId == room.id ? 'selected' : ''}>
                                    ${room.roomName} · ${room.capacity} places
                                    <c:if test="${not empty room.location}"> · ${room.location}</c:if>
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${empty rooms}">
                            <p class="field-hint">No room available for the moment.</p>
                        </c:if>
                    </div>

                    <div class="form-grid">
                        <div class="field">
                            <label for="reservationDate">Date</label>
                            <input type="date" id="reservationDate" name="reservationDate" required>
                        </div>
                        <div class="field">
                            <label for="subject">Object</label>
                            <input type="text" id="subject" name="subject" placeholder="Team Meeting, interview..." required>
                        </div>
                        <div class="field">
                            <label for="startTime">Start</label>
                            <input type="time" id="startTime" name="startTime" required>
                        </div>
                        <div class="field">
                            <label for="endTime">End</label>
                            <input type="time" id="endTime" name="endTime" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary btn-block">Confirm the reservation</button>
                
                    </div>
				</form>
            </div>

        </main>
    </div>

</body>
</html>
