<%
String email = (String) session.getAttribute("email");
String username = (String) session.getAttribute("username");

if (email == null || username == null) {
    response.sendRedirect("Login.jsp");
}
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Account</title>
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
select 
{
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    margin-bottom: 10px;
    box-sizing: border-box;
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
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    margin-bottom: 10px;
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
    margin-bottom: 10px;
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

    <h2>Add Account</h2>
    <form id="AddAccountForm">

        <label for="Type">Account Type:</label>
        <select id="type" name="Accounttype" required>
            <option value="Credit Card">Credit Card</option>
            <option value="Debit Card">Debit Card</option>
            <option value="Bank Account">Bank Account</option>
        </select>
        
        <label for="Name">Account Name:</label>
        <input type="text" id="AccountName" name="AccountName" required>
        
        <button type="button" onclick="AddAccount()"> Add Account </button>
        <center><a href="Dashboard.jsp">Back to Dashboard</a></center>
    </form>
    <script>
        function AddAccount() {
            var AccType = document.getElementById("type").value;
            var AccName = document.getElementById("AccountName").value;
            var email = '<%=session.getAttribute("email")%>';

            $.ajax({
                type: "POST",
                url: "AddAccount",
                data: { AccountType: AccType, AccountName: AccName, Email:email },
                success: function(response) {
                    history.back();
                },
                error: function(error) {
                    document.getElementById("result").innerHTML = "Error in Adding the Account";
                }
            });
        }
    </script>
    

    <div id="result"></div>
</body>
</html>
