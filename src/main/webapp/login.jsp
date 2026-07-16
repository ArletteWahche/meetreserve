<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	body{
		 background:linear-gradient(rgba(0, 51, 102, 0.75), rgba((0, 32, 63, 0.9)),
        url("images/room.jpg") center/cover no-repeat;
        font-family: Arial, sans-serif;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
	}
	.login-container{
		background: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        width: 350px;
	}
	h2{
		text-alig: center;
		margin-bottom: 30px; 
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

    button {
        width: 100%;
        margin-top: 20px;
        padding: 10px;
        border: none;
        border-radius: 5px;
        border: none;
        border-radius: 5px;
        background-color: #2c7be5;
        color: white;
        font-size: 16px;
        cursor: pointer;
   }

   button:hover {
        background-color: #1b5fcc;
   }

   .error {
        color: red;
        margin-top: 15px;
        text-align: center;
    }
    
</style>
</head>
<body>

	<div class="login-container">
		<h2>Login</h2>
		
		<form action="" method="post">
			<label for="email">Email</label>
			<input type="text" id="email" name="email" required>
			
			<label for="password">Password</label>
			<input type="password" id="password" name="password" required>
			
			<button type="submit">Login</button>
		</form>
		
		<% String error = request.getParameter("error");
			if(error != null){
		%>
		<p>Email or Password is not correct</p>
		<%
			}
		%>
	
	</div>

</body>
</html>