<%
String email = (String) session.getAttribute("email");
String username = (String) session.getAttribute("username");
String role= (String) session.getAttribute("role");

if (email != null && username != null) {
    response.sendRedirect("Dashboard.jsp");
}
else if(role != null)
{
    response.sendRedirect("AdminDashboard.jsp");
}
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    background-image: linear-gradient(to left, #8811d1, #fac1ef);
}

form {
    background-color:transparent;
    padding: 20px;
    border-radius: 20px;
    border: 1px solid black;
    box-shadow: 20px 20px 20px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
    width: 300px;
}

h2 {
    text-align: center;
    color: whitesmoke;
}

label {
    display: block;
    margin: 10px 0 5px;
    color: whitesmoke;
}

input {
    width: calc(100% - 12px);
    padding: 8px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

button {
    width: 100%;
    padding: 10px;
    background-color: #4caf50;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-bottom:10px;
}

button:hover {
    background-color: #45a049;
}

a{
    color: black;
}
</style>
</head>
<body>
    <h2>Login</h2>
    <form>
        <label for="loginUsername">Email:</label>
        <input type="text" id="loginUsername" name="Email"required>
        
        <label for="loginPassword">Password:</label>
        <input type="password" id="loginPassword" name="Password" required>
        
        <button type="button" onclick="Login()">Login</button>

        <center>
            <a href="Register.jsp">Don't have an account... Register Now</a><br><br>
            <a href="AdminLogin.jsp">Login as Admin</a>
            <a href="index.jsp">Home</a>
        </center>
    </form>
    
   <script>

    function Login()
    {
    var email=document.getElementById("loginUsername").value;
    var password=document.getElementById("loginPassword").value;
        $.ajax({
        type: "POST",
        url: "Login",
        data: { Email: email, Password: password },
        success: function(response) {
            window.location.href = "Dashboard.jsp";
        },
        error: function(error) {
            document.getElementById("result").innerHTML = "Incorrect Username and Password";
        }
    });
    }
   </script> 

    <div id="result"></div>
</body>
</html>

