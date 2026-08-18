<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log in — MeetReserve</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/solid.min.css" />
    <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@500;600;700&family=Inter:wght@400;500;600;700&family=JetBrains+Mono:wght@500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>

    <div class="login-wrap">
        <div class="login-card">
            <div class="login-mark">MR</div>
            <div class="login-title">Login</div>
            <div class="login-sub">Access the MeetReserve room booking area.</div>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="field">
                    <label for="email">Professional email address</label>
                    <input type="email" id="email" name="email" placeholder="prenom.nom@kozao.net" value="${email}" required>
                </div>
                
                <div class="field">
                    <div class="password-wrapper">
						<label for="password">Password</label>
					    <input type="password" id="password" name="password" autocomplete="new-password" value="${password}" required>
					    <span id="togglePassword" class="toggle-icon">
					        <i class="fa-solid fa-eye"></i>
					    </span>
					</div>
                </div>
                
                <button type="submit" class="btn btn-primary btn-block">Sign in</button>
            </form>
        </div>
    </div>
    
    <script>
	
		const message = document.querySelector('.message');
		const inputs = document.querySelectorAll('input');
	
		inputs.forEach(input => {
		    input.addEventListener('input', function () {
		        if (message) {
		            message.textContent = '';
		        }
		    });
		});
		
		const togglePassword = document.getElementById('togglePassword');
	    const passwordInput = document.getElementById('password');
	    
	    togglePassword.addEventListener('click', function () {
	        const isPassword = passwordInput.type === 'password';
	        passwordInput.type = isPassword ? 'text' : 'password';
	        
	        const icon = this.querySelector('i');
	        icon.classList.toggle('fa-eye');
	        icon.classList.toggle('fa-eye-slash');
	        
	    });
	</script>
</body>
</html>
