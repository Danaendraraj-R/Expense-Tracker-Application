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
    <title>Registration</title>
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
    margin-bottom: 10px;
}

button:hover {
    background-color: #45a049;
}

a{
    color: black;
}
   
.popup {
    display: none; 
    position: fixed;
    top: 10%;
    left: 90%;
    width:250px;
    height:50px;
    transform: translate(-50%, -50%);
    padding: 20px;
    background-color: #208b1c;
    border: 1px solid #f5c6cb;
    border-radius: 5px;
    color: #dce8de;
}

.close {
    position: absolute;
    top: 10px;
    right: 10px;
    cursor: pointer;
}
.OTPverify
{
    display:none;
    
}


</style>
</head>
<body>

    <h2>Registration</h2>
    <div id="successPopup" class="popup">
        <span class="close" onclick="closePopup()">&times;</span>
        <p>OTP sent successfully!</p>
    </div>
    <form id="registerForm">
        <label for="registerUsername">Username:</label>
        <input type="text" id="Username" name="Username" required>

        <label for="Email">Email:</label>
        <input type="email" id="Email" name="Email" required>
        
        <label for="registerPassword">Password:</label>
        <input type="password" id="Password" name="Password" required>

        <div class="OTPVerify" id="OTPVerify">
            
        <label for="OTP">Enter OTP:</label>
        <input type="text" id="OTP" name="OTP" required>

        <button type="button" onclick="register()"> Register </button>

        </div>
        
        <button id="Verify" type="button" onclick="verifyEmail()"> Verify Email </button>
        
         <center>
            <a href="Login.jsp">Already have an account</a><br><br>
            <a href="index.jsp">Home</a>
        </center>
    </form>
    <script>
        function register() {
            var username = document.getElementById("Username").value;
            var email = document.getElementById("Email").value;
            var password = document.getElementById("Password").value;
            var OTP = document.getElementById("OTP").value;
 
        var otp=document.cookie;
        var pin=otp.split("=");
        
        if( pin[1] === OTP)
        {
            $.ajax({
                type: "POST",
                url: "Register",
                data: { Username: username, Email: email, Password: password },
                success: function(response) {
                    document.getElementById("result").innerHTML = "Registration successful!";
                },
                error: function(error) {
                    document.getElementById("result").innerHTML = "Email already exists";
                }
            });
        }
        else
        {
            alert("Invalid OTP");
            return;
        }
      
        }
        function verifyEmail()
        {
            var email = document.getElementById("Email").value;  
          if (!validateEmail(email)) {
                alert("Invalid email format");
                return;
            }
          else
          {
            $("#Verify").hide();
            displayOTP();
            sendOTP(email);
           
          } 
        }
        function sendOTP(email)
        {
            $.ajax({
                type:"POST",
                url: "SendOTP",
                data:{email:email},
                success:function(response){
                    showPopup();
                    console.log("OTP sent successfully");
                },
                error:function(error)
                {
                    document.getElementById("result").innerHTML = "Unable to send OTP"; 
                    console.log(error);  
                }
            });
        }

        function validateEmail(email) {
            var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
            return emailRegex.test(email);
        }
        function showPopup() {
            $("#successPopup").fadeIn();

            setTimeout(function() {
                 closePopup();
            }, 5000); 
        
        
}

function closePopup() {
    $("#successPopup").fadeOut();
}
        function displayOTP()
            {
                $("#OTPVerify").show();
            }
    </script>

    <div id="result"></div>
</body>
</html>

