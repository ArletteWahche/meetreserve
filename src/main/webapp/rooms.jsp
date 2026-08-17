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
                    <a class="nav-item active" href="${pageContext.request.contextPath}/users">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="9" cy="8" r="3.5"/><path d="M3 20c0-3.5 2.7-6 6-6s6 2.5 6 6M16 8a3 3 0 110-6M22 20c0-2.8-2-5-5-5.6"/></svg>
                        Users
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
                    <div class="foot-role">${role}</div>
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
                <div class="eyebrow">Rooms</div>
                <h1 class="page-title">All rooms</h1>
                <p class="page-desc">View available rooms, their capacity, and their location.</p>
            </div>
            
            	

            <c:if test="${not empty param.error}">
                <div class="alert alert-error">${param.error}</div>
            </c:if>
            
            <c:if test="${role == 'ADMINISTRATOR'}">
                <div class="admin-toolbar">
                    <h3>Create a room</h3>
                </div>

                <div class="card" style="padding: 22px;">
                    <form action="${pageContext.request.contextPath}/rooms" method="post">
                        <input type="hidden" name="action" value="create">

                        <div class="form-grid">
                            <div class="field">
                                <label for="nameRoom">Name of the room</label>
                                <input type="text" id="roomName" name="roomName" required>
                            </div>
                            <div class="field" style="grid-column: 1 / -1;">
								<label for="imageUrl">Image URL</label>
								<input type="text" id="imageUrl" name="imageUrl" placeholder="/images/rooms/salle-a.jpg">
							</div>
                            <div class="field">
                                <label for="capacity">Capacity</label>
                                <input type="number" id="capacity" name="capacity" min="1" required>
                            </div>
                            <div class="field">
                                <label for="location">Location</label>
                                <input type="text" id="location" name="location" placeholder="Ex : 2ème étage" required>
                            </div>
                            <div class="field">
                                <label for="available">Availability</label>
                                <select id="available" name="available">
                                    <option value="true">Available</option>
                                    <option value="false">Unavailable</option>
                                </select>
                            </div>
                            <div class="field" style="grid-column: 1 / -1;">
                                <label for="description">Description</label>
                                <input type="text" id="description" name="description" placeholder="Ex : Salle équipée d'un vidéoprojecteur">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary">+ Create a room</button>
                    </form>
                </div>
            </c:if>

            <div class="admin-toolbar">
			    <h3>${fn:length(rooms)} room(s)</h3>
			    <input class="search-input" type="text" id="roomSearch" placeholder="Search a room..." onkeyup="filterRooms()">
			</div>
			
			<div class="room-grid" id="roomGrid">
			    <c:if test="${empty rooms}">
			        <p class="empty-message">No room for the moment.</p>
			    </c:if>
			    <c:forEach var="room" items="${rooms}">
			        <div class="room-card">
			            <div class="room-card-img">
			                <c:choose>
			                    <c:when test="${not empty room.imageUrl}">
			                        <img src="${pageContext.request.contextPath}/${room.imageUrl}" alt="${room.roomName}">
			                    </c:when>
			                    <c:otherwise>
			                        <div class="room-card-noimg">No photo</div>
			                    </c:otherwise>
			                </c:choose>
			            </div>
			            <div class="room-card-body">
			                <strong>${room.roomName}</strong>
			                <p>${room.capacity} places · ${room.location}</p>
			                <c:choose>
			                    <c:when test="${room.available}">
			                        <span class="status-pill confirmed">Free</span>
			                    </c:when>
			                    <c:otherwise>
			                        <span class="status-pill cancelled">Unavailable</span>
			                    </c:otherwise>
			                </c:choose>
			                <div class="room-card-actions">
			                    <a href="${pageContext.request.contextPath}/rooms?id=${room.id}" class="icon-btn" title="Details">→</a>
			                    <c:if test="${role != 'MANAGER' && role != 'ADMINISTRATOR'}">
			                        <a href="${pageContext.request.contextPath}/reservation/new?roomId=${room.id}" class="btn-reserve-sm" title="Reserved">+ Reserve</a>
			                    </c:if>
			                    <c:if test="${role == 'ADMINISTRATOR'}">
							        <form action="${pageContext.request.contextPath}/rooms" method="post" style="display:inline;">
							            <input type="hidden" name="action" value="delete">
							            <input type="hidden" name="id" value="${room.id}">
							            <button type="submit" class="icon-btn" title="Delete" onclick="return confirm('Delete this room?');">✕</button>
							        </form>
							    </c:if>
			                </div>
			            </div>
			        </div>
			    </c:forEach>
			</div>

        </main>
    </div>

    <script>
    function filterRooms() {
        const query = document.getElementById('roomSearch').value.toLowerCase();
        const cards = document.querySelectorAll('#roomGrid .room-card');
        cards.forEach(card => {
            card.style.display = card.textContent.toLowerCase().includes(query) ? '' : 'none';
        });
    }
</script>

</body>
</html>
