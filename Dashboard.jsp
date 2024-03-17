<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
String email = (String) session.getAttribute("email");
String username = (String) session.getAttribute("username");
String role= (String) session.getAttribute("role");
if(role != null)
{
  response.sendRedirect("AdminDashboard.jsp");
}
else if (email == null || username == null) {
    response.sendRedirect("Login.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Dashboard</title>
  <style>
body {
  margin: 0;
  font-family: 'Arial', sans-serif;
  background-image: url("https://thumbs.dreamstime.com/b/lod-israel-july-money-manager-expense-budget-app-play-store-page-smartphone-ceramic-stone-background-jpg-top-view-flat-lay-284679994.jpg");
  background-size: cover;
}


.topnav a {
  margin-top: 10px;
  float: right;
  color: #f2f2f2;
  text-align: center;
  padding: 10px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}
.logout-btn {
  float: right;
  padding: 15px;
}
.logout-btn form {
  margin-top: -5px; 
  height: 2.5%;
}
.logout-btn:hover {
  background-color: #ddd;
  color: black;
}

.logout-btn input[type="submit"] {
  margin-top: 0px;  
  background-color:transparent;
  color: #fff;
  padding: 10px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 17px;;
  height:2.5%;
}
ul
{
  list-style-type: none;
}
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap');
*{
  margin:0px;
  padding:0px;
  box-sizing: border-box;
}
:root{
  --color-text: #616161;
  --color-text-btn: #ffffff;
  --card1-gradient-color1: #f12711;
  --card1-gradient-color2: #f5af19;
  --card2-gradient-color1: #7F00FF;
  --card2-gradient-color2: #E100FF;
  --card3-gradient-color1: #3f2b96;
  --card3-gradient-color2: #a8c0ff;
}
.card-container{
  display: flex;
  font-family: 'Roboto', sans-serif;
  margin-top:1%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  gap: 70px;
}
.accounts-container,.bank-card-container{
  display: flex;
  font-family: 'Roboto', sans-serif;
  margin-top:1%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  gap: 70px;
}

.card-wrap{
  width: 350px;
  height:250px;
  background: #fff;
  border-radius: 20px;
  border: 5px solid #fff;
  overflow: hidden;
  color: var(--color-text);
  box-shadow: rgba(0, 0, 0, 0.19) 0px 10px 20px,
              rgba(0, 0, 0, 0.23) 0px 6px 6px;
  cursor: pointer;
  transition: all .2s ease-in-out;
}
.card-wrap:hover{
  transform: scale(1.02);
}
.card-header{
  height: 200px;
  width: 100%;
  border-radius:100% 0% 100% 0% / 0% 50% 50% 100%;
  display: grid;
  place-items: center;

}

.card-header i{
  color: #fff;
  font-size: 72px;
}
.card-content{
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  margin: 0 auto;
}
.card-title{
  text-align: center;
  text-transform: uppercase;
  font-size: 16px;
  margin-top: 10px;
  margin-bottom: 20px;
  
}
.card-text{
  text-align: center;
  font-size: 12px;
  margin-bottom: 20px;
}
.card-btn{
  border: none;
  border-radius: 100px;
  padding: 5px 30px;
  color: #fff;
  margin-bottom: 15px;
  text-transform: uppercase;
}
.card-header.one{
  background: linear-gradient(to bottom left, var(--card1-gradient-color1), var(--card1-gradient-color2));
}
.card-header.two{
  background: linear-gradient(to bottom left, var(--card2-gradient-color1), var(--card2-gradient-color2));
}
.card-header.three{
  background: linear-gradient(to bottom left, var(--card3-gradient-color1), var(--card3-gradient-color2));
}


.card-btn.one{
  background: linear-gradient(to left, var(--card1-gradient-color1), var(--card1-gradient-color2));
}
.card-btn.two{
  background: linear-gradient(to left, var(--card2-gradient-color1), var(--card2-gradient-color2));
}
.card-btn.three{
  background: linear-gradient(to left, var(--card3-gradient-color1), var(--card3-gradient-color2));
}
.typewriter {
      overflow: hidden;
      white-space: nowrap;
      font-size: 24px;
      text-align: center;
      color:white;
      margin-top: 2%;
    }
    .navbar
    {
      justify-content: space-between;
      display:flex;
      background-color: rgb(32, 23, 19);
      height: 2.5%;
      overflow: hidden;
      width:100%
    }
.library a {
  margin-top: 10px;
  float:left;
  color: #f2f2f2;
  text-align: center;
  padding: 10px 10px;
  text-decoration: none;
  font-size: 17px;
}

.library a:hover {
  background-color: #ddd;
  color: black;
}
  </style>
  <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
  <script>
    
function toggleNav() {
  var container = document.querySelector('.container');
  container.classList.toggle('change');
}

  </script>
  <script>
    document.addEventListener("DOMContentLoaded", function() {
      var textElement = document.getElementById("text");
      var text = textElement.innerHTML;
      textElement.innerHTML = ""; 
  
      var i = 0;
      var speed = 50; 
  
      function typeWriter() {
        if (i < text.length) {
          textElement.innerHTML += text.charAt(i);
          i++;
          setTimeout(typeWriter, speed);
        }
      }
  
      typeWriter(); 
    });

    $(document).ready(function () {
        var Id = '<%=session.getAttribute("email") %>';
        var total=0;
        var Income=0;
        var Expense=0;
        

        $.ajax({
            url: "TransactionData",
            type: "GET",
            dataType: "json",
            success: function (data) {
                total=data.length;

                filterIncome(data);
                filterExpense(data);

                
            },
            error: function (error) {
                console.log("Error fetching data: " + error);
            }
        });
        $.ajax({
            url: "BankData",
            type: "GET",
            dataType: "json",
            success: function (data) {
                printCards(data);
                
            },
            error: function (error) {
                console.log("Error fetching data: " + error);
            }
        });

        function filterIncome(Data)
        {
            var IncomeData = Data.filter(function (Data) {
                    
                    return Data.Type === "Income";
        
                }); 
            
            for(var i=0;i<IncomeData.length;i++)
            {
                Income+=IncomeData[i].Amount;
            }    
        }
        function filterExpense(Data)
        {
            var ExpenseData = Data.filter(function (Data) {
                    
                    return Data.Type === "Expense";
        
                }); 
                
            for(var i=0;i<ExpenseData.length;i++)
            {
                Expense+=ExpenseData[i].Amount;
            }

            total=Income-Expense;
            console.log(total);
            document.getElementById("Income").innerHTML= Income;
            document.getElementById("expenses").innerHTML= Expense ;
            document.getElementById("total").innerHTML= total ;
            
        }

        function printCards(data) {
    var container = $("#bank-card-container");
    container.empty();

    if (data.length === 0) {
        
    } else 
    {
      for (var i = 0; i < data.length; i++) {
        var cardHTML = "<div class='card-wrap' onclick='ViewTransactionHistory(\"" + data[i].AccountId+ "\")'>"
    + "<div class='card-header three' id='" + data[i].AccountId + "'>"
    + "<i id='balance-" + i + "'>" + data[i].Balance + "</i>" + "</div>"
    + "<div class='card-content'>" + "<h1 class='card-title'>" + data[i].BankName +"-"+data[i].AccountType + "</h1>" + "</div>";

container.append(cardHTML);

}

    }

}


        
            
   
        document.getElementById("Wallet").innerHTML= '<%=session.getAttribute("balance")%>';
        
    });


    function Income()
    {
        window.location.href="Income.jsp";
    }
    function Expense()
    {
        window.location.href="Expense.jsp";
    }
    function ViewTransactionHistory(accountId) {
    window.location.href = "TransactionHistory.jsp?accountId=" + accountId;
}
  function AllTransactions()
  {
    window.location.href="ViewTransaction.jsp";
  }

  </script>
 </head>
<body>


<div class="navbar">
  <div class="library">
    <a href="LibraryManagement.jsp">Library Management</a> 
  </div> 
   
  <div class="topnav">
    <div class="logout-btn">
        <form action="Logout" method="post">
            <input type="submit" value="Logout">
        </form>
      </div>
    <a href="Profile.jsp">View Profile</a>   
    <a href="ViewTransaction.jsp">Transaction History</a>
    <a href="AddAccount.jsp">Add Account</a>
    <a href="AddTransaction.jsp">Add Transaction</a>  
  </div>
  </div>
  
  <div class="card-container">
  <div class="card-wrap" onclick="AllTransactions()">
    <div class="card-header one">
      <i id="total"></i>
    </div>
    <div class="card-content">
      <h1 class="card-title">Total Balance</h1>
   </div>
  </div>
  <div class="card-wrap" onclick="Income()">
    <div class="card-header two">
      <i id="Income"></i>
    </div>
    <div class="card-content">
      <h1 class="card-title">Income</h1>
   </div>
  </div>
  <div class="card-wrap" onclick="Expense()">
    <div class="card-header three">
      <i id="expenses"></i>
    </div>
    <div class="card-content">
      <h1 class="card-title">Expenditure</h1>
   </div>
  </div>

  </div>
  <div class="typewriter">
    <h1 id="text">"Here you can manage and view your day to day expenses..."</h1>
</div>
</div>
 <div class="accounts-container">
    <div class="card-wrap" onclick="ViewTransactionHistory('Wallet')">
        <div class="card-header three">
          <i id="Wallet"></i>
        </div>
        <div class="card-content">
          <h1 class="card-title">Wallet Balance</h1>
       </div>
      </div>
      <div id="bank-card-container" class="bank-card-container"> </div>
 </div>

  

</body>
</html>