<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// Retrieve values from the session
String email = (String) session.getAttribute("email");
String username = (String) session.getAttribute("username");

if (email == null || username == null) {
    response.sendRedirect("Login.jsp");
}

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Internal Transaction</title>
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

        .task-container {
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

        label {
            font-weight: bold;
            color: #333;
            display: block;
            margin-top: 10px;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        button {
            background-color: #4caf50;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
        a{
            color:black;
        }
        body{
            background-image: linear-gradient(to left,#f74049,#f8a7cb);
        }



    </style>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <script>
        $(document).ready(function() {
            $.ajax({
            url: "BankData",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var selectField = $("#Accounts");
                selectField.empty();
                selectField.append("<option></option>");
                $.each(data, function(Account,data) {
                    selectField.append("<option value='" + data.AccountId + "'>" + data.BankName + "</option>");
                });
                selectField.append("<option value='Wallet'>Wallet</option>");
                var selectField = $("#Accounts1");
                selectField.empty();
                selectField.append("<option></option>");
                $.each(data, function(Account,data) {
                    selectField.append("<option value='" + data.AccountId + "'>" + data.BankName + "</option>");
                });
                selectField.append("<option value='Wallet'>Wallet</option>");
                
            },
            error: function (error) {
                console.log("Error fetching data: " + error);
            }
           });
        }); 
        function handleSelectChange(changedSelectId, targetSelectId) {
      var changedSelect = document.getElementById(changedSelectId);
      var targetSelect = document.getElementById(targetSelectId);
      var selectedValue = changedSelect.value;
      targetSelect.selectedIndex = 0;
      for (var i = 0; i < targetSelect.options.length; i++) {
        var option = targetSelect.options[i];
        if (option.value === selectedValue) {
          option.style.display = 'none';
        } else {
          option.style.display = '';
        }
      }
    }  
        
            </script>

</head>
<body>

<div class="task-container">
    <h2>Internal Transaction</h2>

    <form action="ITransaction" method="post">


        <label>From</label>
        <select name="From" id="Accounts" onchange="handleSelectChange('Accounts', 'Accounts1')" required>      
        </select>

        <label>To</label>
        <select name="To" id="Accounts1" onchange="handleSelectChange('Accounts1', 'Accounts2')"required>      
        </select>

        <input type="hidden" name="user" value=<%=email %> >

        
        <label>Date:</label>
        <input type="date" name="tDate" required>

        <label>Amount:</label>
        <input type="number" name="Amount" required>

        <button type="submit">Add Transaction</button>
    </form>
<br>
    <a href="Dashboard.jsp">Back to Dashboard</a>
    
</div>



</body>
</html>
