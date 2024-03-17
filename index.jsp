
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
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <title>Expense Tracker Application</title>
<style>
body{
    background-image: linear-gradient(to left, #071222, #291e62);
    background-repeat: no-repeat;
    background-size: cover;
    height:100vh;
}    

.topnav {
  background-color: transparent;
  height: 55px;
  overflow: hidden;
  width:100%
}

.topnav a {
  float: right;
  height: 55px;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  height: 55px;
  color: black;
}
h1 {
  margin-top: 160px;  
  font-size: 70px;
  font-weight: 600;
  font-family: fantasy;
  background-image: linear-gradient(to left, #aed4cc, #368fc2);
  color: transparent;
  background-clip: text;
  -webkit-background-clip: text;
  text-align: center;
}
.container{
    text-align: center;
    height:120px;
    width:600px; 
    margin-left:390px;
    color: whitesmoke;
    font-family: arial,cursive;
}



</style>
</head>
<body>
<div class="topnav">   
<a href="Login.jsp"> <b>Login </b></a> 
<a href="Register.jsp"><b> Register</b> </a>
</div>
<h1>Expense Tracker application</h1>

<div class="container">
<h3>"Track your expenses, control your spending "</h3>
</div>

</body>
</html>
