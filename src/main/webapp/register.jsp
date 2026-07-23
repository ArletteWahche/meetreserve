<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/solid.min.css" />
<style type="text/css">

body{
		background:url("${pageContext.request.contextPath}/images/room1.jpg") center/cover no-repeat;
        font-family: Inter;
        color: #fff;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
	}
	.register-container{
		background-color: rgba(0, 128, 0, 0.8);
        padding: 30px;
        border-radius: 30px;
        box-shadow: 1px 5px 25px;
        width: 350px;
        
	}
	h2{
		text-align: center;
		margin-bottom: 30px;
		font-family:  'Forte', cursive; 
		color: #fff;
	}
	label {
        display: block;
        margin-bottom: 6px;
        margin-top: 12px;
    }

    input {
        width: 100%;
        padding: 10px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
    .password-wrapper {
		position: relative;
	}
	.password-wrapper input {
		padding-right: 40px;
	}
	.toggle-icon {
    position: absolute;
    right: 12px;
    bottom: 12px;
    cursor: pointer;
    font-size: 16px;
    color: #666;
    user-select: none;
}

    button {
        width: 100%;
        margin-top: 20px;
        padding: 10px;
        border: none;
        border-radius: 20px;
        border: none;
        background-color: #2c7be5;
        color: white;
        font-size: 16px;
        cursor: pointer;
   }

   button:hover {
        background-color: #1b5fcc;
   }
   
    .message {
        color: red;
        margin-top: 15px;
        text-align: center;
    }

</style>

</head>
<body>
	<div class="register-container">
		<h2>Registration</h2>
		
		<form action="${pageContext.request.contextPath}/register" method="post">
			<label for="name">Name</label>
			<input type="text" id="name" name="name" required>
			
			<label for="surname">Surname</label>
			<input type="text" id="surname" name="surname" required>
			
			<label for="email">Email</label>
			<input type="email" id="email" name="email" required autocomplete="email">
			
			<div class="password-wrapper">
				<label for="password">Password</label>
			    <input type="password" id="password" name="password" autocomplete="new-password" required>
			    <span id="togglePassword" class="toggle-icon">
			        <i class="fa-solid fa-eye"></i>
			    </span>
			</div>
			
			<button type="submit">Register</button>
		</form>
		
		<p>Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Sign In</a></p>
		
		<p class="message">${message}</p>
		
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