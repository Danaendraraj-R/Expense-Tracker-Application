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
    <title>Add Transaction</title>
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
.form-popup
{
  position:fixed;
  bottom:0;
  right:15px;
  border:3px solid #f1f1f1;
  z-index:9;
  display:none;
  
}

.form-container
{
  width:300px;
  padding:20px;
  background:#fff;
  
}
.form-conatianer  input[type=text]
{ 
  padding:12px;
  margin:5px 0 25px;
}

.form-container .btn
{
  background-color:#4CAF50;
   color:#fff;
  padding:16px 12px;
  border:none;
  width:100%;
  margin:10px 0px ;
}

.close
{
   background:transparent;
   padding:12px;
   right:2px;
   top:-2.5px;
   position:absolute;
   border-radius:50%;
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
                $.each(data, function(Account,data) {
                    selectField.append("<option value='" + data.AccountId + "'>" + data.BankName + "</option>");
                });
                selectField.append("<option value='Wallet'>Wallet</option>");
                
            },
            error: function (error) {
                console.log("Error fetching data: " + error);
            }
           });
            
            function fetchCategories() {
                $.ajax({
                    url: "GetCategories", 
                    type: "GET",
                    dataType: "json",
                    success: function(data) {
                        var selectField = $("#Category");
                        selectField.empty();
                        $.each(data, function(Category,data) {
                            selectField.append("<option value='" + data.Category + "'>" + data.Category + "</option>");
                        });
                        
                    },
                    error: function(error) {
                        console.log("Error fetching users: " + error);
                    }
                });
            }
            fetchCategories();
        });
        function openForm()
        {
          document.getElementById("form").style.display="block";
         }

        function closeForm()
        {
        document.getElementById("form").style.display="none";
        }
        function AddCategory() {
            var Category = document.getElementById("newCategory").value;
            var Email = document.getElementById("Email").value;

            $.ajax({
                type: "POST",
                url: "AddCategory",
                data: { Category: Category, Email: Email },
                success: function(response) {
                    closeForm();
                    location.reload();
                },
                error: function(error) {
                    document.getElementById("result").innerHTML = "Error";
                }
            });
        }
            </script>

</head>
<body>

<div class="task-container">
    <h2>Add Transaction</h2>

    <form action="AddTransaction" method="post">

        <label>Select Category:</label>
        <select name="Category" id="Category" required>
        </select>

        <label>Select Type:</label>
        <select type="transactiontype" id="type" name="type" required>
            <option value="Income">Income</option>
            <option value="Expense">Expense</option>
        </select>

        <label>Select Account</label>
        <select name="Account" id="Accounts" required> 
            
        </select>

        <input type="hidden" name="user" value=<%=email %> >

        <label>Date:</label>
        <input type="date" name="tDate" required>

        <label>Amount:</label>
        <input type="number" name="Amount" required>

        <button type="submit">Add Transaction</button>
        <button type='button' onclick=openForm()>Add Category</button><br><br>
        <button><a style=" text-decoration:none; color:#fff"  href="Internal.jsp">Internal Transactions</a></button>
    </form>
<br>
    <a href="Dashboard.jsp">Back to Dashboard</a>
    
</div>

<div class="form-popup" id="form">  
  <form class="form-container">
    <span class="close" onclick="closeForm()"> &#10005; </span>  
    <h2> Add Category </h2>   
    <label for="email"> Email</label>     
    <input type="text" value='<%=session.getAttribute("email")%>' name="email" id="Email" readonly/>    
    <br> 
    <label for="password">Category </label>    
    <input type="text" name="newCategory" id="newCategory" placeholder="Enter Category"/> 
    <br>  
    <center><button type="button" onclick=AddCategory()> Add Category</button> </center>
    <div id="result"></div>
  </form> 
  
  </div> 


</body>
</html>
