<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String email = (String) session.getAttribute("email");
String username = (String) session.getAttribute("username");

if (email == null || username == null) {
    response.sendRedirect("Login.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Income</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #4caf50;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #4caf50;
            color: #fff;
        }

        a {
            text-decoration: none;
            color: #fff;
            cursor: pointer;
            background-color: #4caf50;
            width: 120px;
            display: inline-block;
            text-align: center;
            padding: 8px;
        }
        .heading{
            display:flex;
            justify-content: space-between;
        }
       .heading .loggedhours{
            display:flex;
            text-align: right;
            border: 1px solid #4caf50;
            padding: 8px;
            border-radius: 4px;
            margin-right:20px;
            background-color: aliceblue;
        }
        .export-btn
        {
            background-color: #04AA6D; /* Green */
            border: none;
            color: white;
            padding: 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 12px;
        }
        .update-btn
        {
            background-color: #f3ef27; 
            border: 1px solid;
            color: black;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            cursor: pointer;
            border-radius: 4px;
        }
        
    </style>
</head>
<body>
<div class="heading">
    <h2>View Income</h2>
    <div class="export-container"> 
        <button class="export-btn" onclick="exportData('PDF')">Export to PDF</button>
        <button class="export-btn" onclick="exportData('CSV')">Export to CSV</button>
        <button class="export-btn" onclick="exportData('HTML')">Export to Html</button>
    </div>
</div>


<div id="dataContainer"></div>

<center><a href="Dashboard.jsp">Back</a></center>

<script type="text/javascript">
function exportData(Type)
    {
        $.ajax({
            url: "TransactionData",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var Data=data.filter(function(data)
                {
                    return data.Type =="Income";
                });
                if(Type === "PDF")
                {
                    exportToPdf(Data);
                }
                else if(Type === "CSV")
                {
                    exportToCSV(Data);
                }
                else if(Type === 'HTML')
                {
                    exportToHtml(Data);
                }
                
            },
            error: function (error) {
                console.log("Error fetching data: " + error);
            }
        });
    }
    
    function exportToPdf(data) {
    var form = document.createElement('form');
    form.style.display = 'none'; 
    form.method = 'POST';
    form.action = 'PdfExport';
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'data';
    input.value = JSON.stringify(data);
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form);
}
function exportToCSV(data) {
    var form = document.createElement('form');
    form.style.display = 'none'; 
    form.method = 'POST';
    form.action = 'CsvExport';
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'data';
    input.value = JSON.stringify(data);
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form);
}
function exportToHtml(data) {
    var form = document.createElement('form');
    form.style.display = 'none'; 
    form.method = 'POST';
    form.action = 'HtmlExport';
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'data';
    input.value = JSON.stringify(data);
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form);
}
function UpdateTransaction(id)
{
    window.location.href="UpdateTransaction.jsp?data="+id;   
}

    
    $(document).ready(function () {
        $.ajax({
            url: "TransactionData",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var Data=data.filter(function(data)
                {
                    return data.Type =="Income";
                });
                displayData(Data);
            },
            error: function (error) {
                console.log("Error fetching data: " + error);
            }
        });

        function displayData(data) {
            var container = $("#dataContainer");
            container.empty();
            var num=1;

            if (data.length === 0) {
                container.append("<p>No transactions available for this User</p>");
            } else {
               var table = "<table border='1'><tr><th>SNo</th><th>Type</th><th>Category</th><th>Amount</th><th>Date</th><th>Action</th></tr>";
                for (var i = 0; i < data.length; i++) {
                    table += "<tr><td>" + num + "</td><td>" + data[i].Type + "</td><td>" + data[i].Category + "</td><td>" + data[i].Amount + "</td><td>" + data[i].Date + "</td><td>"+
                        "<button class='update-btn' onclick='UpdateTransaction("+data[i].TransactionId+")'>Update</button></td></tr>";
                    num++;
                }    
                table += "</table>";
                container.append(table);
            }
        }

      
        
    });
</script>

</body>
</html>
