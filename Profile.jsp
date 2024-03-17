
<%
String email = (String) session.getAttribute("email");
String username = (String) session.getAttribute("username");
double balance= (double) session.getAttribute("balance");


if (email == null || username == null ) {
    response.sendRedirect("Login.jsp");
}


%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .profile-container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 400px;
            text-align: center;
        }

        h2 {
            color: #4caf50;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #4caf50;
            color: #fff;
        }

        label {
            font-weight: bold;
            color: #333;
            display: block;
            margin-top: 10px;
        }

        p {
            margin: 5px 0;
            color: #666;
        }

        a {
            text-decoration: none;
            color: #4caf50;
            font-weight: bold;
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #fff;
            border: 2px solid #4caf50;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }

        a:hover {
            background-color: #4caf50;
            color: #fff;
        }

        

        #fileInput {
            display: none;
        }
        body{
            background-image: linear-gradient(to left,#6ff6b3e2,#69e0f8);
        }
    </style>
</head>
<body>

<div class="profile-container">

    <h2>User Profile</h2>


    

    <table>
        
        <tr>
            <td><label>Username:</label></td>
            <td><p><%=username %></p></td>
        </tr> 
        <tr>
            <td><label>Email:</label></td>
            <td><p><%= email %></p></td>
        </tr> 
        <tr>
            <td><label>Wallet Balance:</label></td>
            <td><p><%=balance%></p></td>
        </tr> 
    </table>

        <a href="Dashboard.jsp">Back</a>
</div>

</body>
</html>
