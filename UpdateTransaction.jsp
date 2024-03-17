<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String email = (String) session.getAttribute("email");
String username = (String) session.getAttribute("username");

if (email == null || username == null) {
    response.sendRedirect("Login.jsp");
}

int id=Integer.parseInt(request.getParameter("data"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Transaction</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
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

input, select {
    width: calc(100% - 12px);
    padding: 8px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit] {
    width: 100%;
    padding: 10px;
    background-color: #4caf50;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #45a049;
}

a{
    color: black;
}

</style>
<body>
    <script>

document.addEventListener("DOMContentLoaded", function () {
    var id = '<%=id%>';

    $.ajax({
        url: "TransactionData",
        type: "GET",
        dataType: "json",
        success: function (data) {
            var Data = data.filter(function (data) {
                return data.TransactionId == id;
            });

            document.getElementById("transactionId").value = Data[0].TransactionId;
            document.getElementById("transactionamount").value = Data[0].Amount;
            document.getElementById("category").value = Data[0].Category;
            document.getElementById("via").value = Data[0].Via;
            document.getElementById("type").value = Data[0].Type;
        },
        error: function (error) {
            console.log("Error fetching data: " + error);
        }
    });
});
</script>

<h2>Update Transaction</h2>

<form id="updateTransactionForm" action="UpdateTransaction" method="post">
    <label for="transaction">Transaction ID:</label>
    <input type="text" id="transactionId" name="transactionId" readonly><br>

    <label for="transactionold">Old Amount:</label>
    <input type="number" id="transactionamount" name="oldamount" readonly><br>

    <input type="hidden" name="Category" id="category" value="">
    <input type="hidden" name="via" id="via" value="">
    <input type="hidden" name="type" id="type" value="">


    <label for="status">New Amount:</label>
    <input type="number" id="newAmount" name="newAmount">

    
    

    <input type="submit" value="Update Transaction">
</form>



</body>
</html>
