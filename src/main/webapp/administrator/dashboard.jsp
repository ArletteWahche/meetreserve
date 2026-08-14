<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration — MeetReserve</title>
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
                    <div class="brand-sub">Administration</div>
                </div>
            </div>

            <div>
                <div class="nav-group-label">General</div>
                <div class="nav">
                    <a class="nav-item active" href="${pageContext.request.contextPath}/dashboard/administrator">
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
                    <div class="foot-role">Administrator</div>
                </div>
                <form action="${pageContext.request.contextPath}/logout" method="post" style="margin-left:auto;">
                    <button type="submit" class="logout-link" style="background:none;border:none;cursor:pointer;">Logout</button>
                </form>
            </div>
        </aside>

        <main class="main">

            <div class="page-head">
                <div class="eyebrow">Administration</div>
                <h1 class="page-title">User Management</h1>
                <p class="page-desc">Create employee, manager, or administrator accounts, and find all existing accounts here. ✅</p>
            </div>

            <c:if test="${not empty param.success}">
                <div class="alert alert-success">User created successfully. ✅</div>
            </c:if>
            <c:if test="${not empty param.error}">
                <div class="alert alert-error">${param.error}</div>
            </c:if>

            <div class="grid-3">
                <div class="card stat-card">
                    <div class="stat-label">Total users</div>
                    <div class="stat-value">${fn:length(users)}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Employees</div>
                    <div class="stat-value">
                        <c:set var="employeeCount" value="0"/>
                        <c:forEach var="u" items="${users}">
                            <c:if test="${u.role == 'EMPLOYEE'}"><c:set var="employeeCount" value="${employeeCount + 1}"/></c:if>
                        </c:forEach>
                        ${employeeCount}
                    </div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Managers</div>
                    <div class="stat-value">
                        <c:set var="managerCount" value="0"/>
                        <c:forEach var="u" items="${users}">
                            <c:if test="${u.role == 'MANAGER'}"><c:set var="managerCount" value="${managerCount + 1}"/></c:if>
                        </c:forEach>
                        ${managerCount}
                    </div>
                </div>
            </div>
            
             <!-- ===== Formulaire de création de salle ===== -->
            <div class="admin-toolbar">
                <h3>Create a room</h3>
            </div>

            <div class="card" style="padding: 22px;">
                <form action="${pageContext.request.contextPath}/rooms" method="post">
                    <input type="hidden" name="action" value="create">

                    <div class="form-grid">
                        <div class="field">
                            <label for="nameRoom">Room name</label>
                            <input type="text" id="roomName" name="roomName" required>
                        </div>
                        <div class="field">
                            <label for="capacity">Capacity</label>
                            <input type="number" id="capacity" name="capacity" min="1" required>
                        </div>
                        <div class="field">
                            <label for="location">Location</label>
                            <input type="text" id="location" name="location" placeholder="Ex : Second floor" required>
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
                            <input type="text" id="description" name="description" placeholder="Ex :  Room with a projector">
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">+ Create a room</button>
                </form>
            </div>

            <div class="admin-toolbar">
                <h3>All the rooms</h3>
            </div>

            <div class="card">
                <table>
                    <thead>
                        <tr>
                        	<th>Name</th>
                        	<th>Capacity</th>
                        	<th>Location</th>
                        	<th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty rooms}">
                                <tr class="empty-row"><td colspan="4">No room for the moment.</td></tr>
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
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- ===== Formulaire de création ===== -->
            <div class="admin-toolbar">
                <h3>Create a user</h3>
            </div>

            <div class="card" style="padding: 22px;">
                <form action="${pageContext.request.contextPath}/dashboard/administrator" method="post">
                    <input type="hidden" name="action" value="createUser">

                    <div class="form-grid">
                        <div class="field">
                            <label for="name">Surname</label>
                            <input type="text" id="name" name="name" required>
                        </div>
                        <div class="field">
                            <label for="surname">Name</label>
                            <input type="text" id="surname" name="surname" required>
                        </div>
                        <div class="field">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" required>
                        </div>
                        <div class="field">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" required minlength="6">
                            <p class="field-hint">Minimum 6 characters. Please share these login credentials with the user. ⭐</p>
                        </div>
                        <div class="field" style="grid-column: 1 / -1;">
                            <label for="role">Role</label>
                            <select id="role" name="role" required>
                                <c:forEach var="r" items="${roles}">
                                    <option value="${r}">${r}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">+ Create the user</button>
                </form>
            </div>

            <!-- ===== Liste des utilisateurs ===== -->
            <div class="admin-toolbar">
                <h3>All the users</h3>
                <input class="search-input" type="text" id="userSearch" placeholder="Rechercher un utilisateur..." onkeyup="filterUsers()">
            </div>

            <div class="card">
                <table id="usersTable">
                    <thead>
                        <tr>
                        	<th>Name</th>
                        	<th>Email</th>
                        	<th>Role</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty users}">
                                <tr class="empty-row"><td colspan="3">No user for the moment.</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="u" items="${users}">
                                    <tr>
                                        <td><strong>${u.name} ${u.surname}</strong></td>
                                        <td>${u.email}</td>
                                        <td><span class="status-pill ${u.role}">${u.role}</span></td>
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
        function filterUsers() {
            const query = document.getElementById('userSearch').value.toLowerCase();
            const rows = document.querySelectorAll('#usersTable tbody tr');
            rows.forEach(row => {
                row.style.display = row.textContent.toLowerCase().includes(query) ? '' : 'none';
            });
        }
    </script>

</body>
</html>
