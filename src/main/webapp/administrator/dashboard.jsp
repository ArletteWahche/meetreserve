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
                    <a class="nav-item" href="${pageContext.request.contextPath}/users">
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
                    ${fn:substring(currentUser.name, 0, 1)}${fn:substring(currentUser.surname, 0, 1)}
                </div>
                <div>
                    <div class="foot-name">${currentUser.name} ${currentUser.surname}</div>
                    <div class="foot-role">Administrator</div>
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
                <h1 class="page-title">Administrator Dashboard</h1>
                <p class="page-desc">Application Overview. Manage users and rooms from the menu on the left.</p>
            </div>

            <div class="grid-3">
                <div class="card stat-card">
                    <div class="stat-label">Total users</div>
                    <div class="stat-value">${totalUsers}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Employees</div>
                    <div class="stat-value">${employeeCount}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Managers</div>
                    <div class="stat-value">${managerCount}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Rooms</div>
                    <div class="stat-value">${totalRooms}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Total reservations</div>
                    <div class="stat-value">${totalReservations}</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-label">Pending validation</div>
                    <div class="stat-value">${pendingCount}</div>
                </div>
            </div>

            <div class="admin-toolbar">
                <h3>Quick Access</h3>
            </div>

            <div class="grid-2">
                <a href="${pageContext.request.contextPath}/users" class="card" style="padding: 22px; text-decoration:none; color:inherit;">
                    <h3 style="margin:0 0 6px;">Manage users →</h3>
                    <p style="margin:0; color:var(--muted); font-size:13px;">Create an employee, manager, or administrator account, and view the list.</p>
                </a>
                <a href="${pageContext.request.contextPath}/rooms" class="card" style="padding: 22px; text-decoration:none; color:inherit;">
                    <h3 style="margin:0 0 6px;">Manage rooms →</h3>
                    <p style="margin:0; color:var(--muted); font-size:13px;">Create a new room, view the list, and check their availability.</p>
                </a>
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
