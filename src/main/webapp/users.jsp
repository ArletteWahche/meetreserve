<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Users — MeetReserve</title>
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
                    <a class="nav-item" href="${pageContext.request.contextPath}/dashboard/administrator">
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
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit" class="btn-logout-top">Logout</button>
                </form>
            </div>

            <div class="page-head">
                <div class="eyebrow">Administration</div>
                <h1 class="page-title">Users</h1>
                <p class="page-desc">Create accounts for employees, managers, or administrators, and view the complete list of existing accounts here.</p>
            </div>

            <c:if test="${not empty param.success}">
                <div class="alert alert-success">User created successfully.</div>
            </c:if>
            <c:if test="${not empty param.error}">
                <div class="alert alert-error">${param.error}</div>
            </c:if>

            <div class="admin-toolbar">
			    <h3 id="formTitle">Create a user</h3>
			</div>
			
			<div class="card" style="padding: 22px;">
			    <form id="userForm" action="${pageContext.request.contextPath}/users" method="post">
			        <input type="hidden" id="formAction" name="action" value="create">
			        <input type="hidden" id="originalEmail" name="originalEmail" value="">
			
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
			            <div class="field" id="passwordField">
			                <label for="password">Password</label>
			                <input type="password" id="password" name="password" required minlength="6">
			                <p class="field-hint">Minimum 6 characters. Share these credentials with the user yourself.</p>
			            </div>
			            <div class="field">
			                <label for="role">Role</label>
			                <select id="role" name="role" required onchange="toggleManagerField()">
			                    <c:forEach var="r" items="${roles}">
			                        <option value="${r}">${r}</option>
			                    </c:forEach>
			                </select>
			            </div>
			            <div class="field" id="managerField" style="display:none;">
			                <label for="managerId">Manager</label>
			                <select id="managerId" name="managerId">
			                    <option value=""> None </option>
			                    <c:forEach var="m" items="${managers}">
			                        <option value="${m.id}">${m.name} ${m.surname}</option>
			                    </c:forEach>
			                </select>
			            </div>
			        </div>
			
			        <button type="submit" class="btn btn-primary" id="formSubmitBtn">+ Create a user</button>
			        <button type="button" class="btn" id="cancelEditBtn" style="display:none;" onclick="resetForm()">Cancel</button>
			    </form>
			</div>
			
			<div class="admin-toolbar">
			    <h3>${fn:length(users)} user(s)</h3>
			    <input class="search-input" type="text" id="userSearch" placeholder="Rechercher un utilisateur..." onkeyup="filterUsers()">
			</div>
			
			<div class="card">
			    <table id="usersTable">
			        <thead>
			            <tr>
			            	<th>Name</th>
			            	<th>Email</th>
			            	<th>Role</th>
			            	<th></th>
			            </tr>
			        </thead>
			        <tbody>
			            <c:choose>
			                <c:when test="${empty users}">
			                    <tr class="empty-row"><td colspan="4">No user for the moment.</td></tr>
			                </c:when>
			                <c:otherwise>
			                    <c:forEach var="u" items="${users}">
			                        <tr>
			                            <td><strong>${u.name} ${u.surname}</strong></td>
			                            <td>${u.email}</td>
			                            <td><span class="status-pill ${u.role}">${u.role}</span></td>
			                            <td class="row-actions">
			                                <button type="button" class="icon-btn" title="Edit"
			                                        onclick="editUser('${u.email}','${u.name}','${u.surname}','${u.role}','${u.managerId}')">✎</button>
			                                <form action="${pageContext.request.contextPath}/users" method="post" style="display:inline;">
			                                    <input type="hidden" name="action" value="delete">
			                                    <input type="hidden" name="email" value="${u.email}">
			                                    <button type="submit" class="icon-btn" title="Delete"
			                                            onclick="return confirm('Delete this user?');">✕</button>
			                                </form>
			                            </td>
			                        </tr>
			                    </c:forEach>
			                </c:otherwise>
			            </c:choose>
			        </tbody>
			    </table>
			</div>
	</main>
    <script>
		function filterUsers() {
		    const query = document.getElementById('userSearch').value.toLowerCase();
		    document.querySelectorAll('#usersTable tbody tr').forEach(row => {
		        row.style.display = row.textContent.toLowerCase().includes(query) ? '' : 'none';
		    });
		}
		
		function toggleManagerField() {
		    const role = document.getElementById('role').value;
		    document.getElementById('managerField').style.display = role === 'EMPLOYEE' ? '' : 'none';
		}
		
		function editUser(email, name, surname, role, managerId) {
		    document.getElementById('formTitle').textContent = 'Edit user';
		    document.getElementById('formAction').value = 'update';
		    document.getElementById('originalEmail').value = email;
		    document.getElementById('name').value = name;
		    document.getElementById('surname').value = surname;
		    document.getElementById('email').value = email;
		    document.getElementById('email').readOnly = true;
		    document.getElementById('role').value = role;
		    toggleManagerField();
		    if (managerId && managerId !== 'null' && managerId !== '') {
		        document.getElementById('managerId').value = managerId;
		    }
		    document.getElementById('password').required = false;
		    document.getElementById('passwordField').style.display = 'none';
		    document.getElementById('formSubmitBtn').textContent = 'Save changes';
		    document.getElementById('cancelEditBtn').style.display = '';
		    document.getElementById('userForm').scrollIntoView({behavior:'smooth'});
		}
		
		function resetForm() {
		    document.getElementById('userForm').reset();
		    document.getElementById('formTitle').textContent = 'Create a user';
		    document.getElementById('formAction').value = 'create';
		    document.getElementById('originalEmail').value = '';
		    document.getElementById('email').readOnly = false;
		    document.getElementById('password').required = true;
		    document.getElementById('passwordField').style.display = '';
		    document.getElementById('formSubmitBtn').textContent = '+ Create a user';
		    document.getElementById('cancelEditBtn').style.display = 'none';
		    toggleManagerField();
		}
	</script>

</body>
</html>


