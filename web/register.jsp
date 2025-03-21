<%-- 
    Document   : Register
    Created on : Mar 16, 2025, 7:34:45 PM
    Author     : Oanh Nguyen
--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="css/register.css"> <!-- CSS ri�ng -->
        <!-- Th�m FontAwesome b?n m?i nh?t -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
              integrity="sha512-1zcF+2uiG9q4CPGK/c/pX9nuSUXGKmzME2YkdE+5EYXPLkZXl2b7xvFeoJq6Digw1VE3D6Z0fjQRPtP2z5aXvg==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />

    </head>
    <body>
        <div class="register-container">
            <h1>Register</h1>
            <!-- Hi?n th? th�ng b�o l?i -->
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <!-- Hi?n th? th�ng b�o th�nh c�ng -->
            <c:if test="${not empty successMsg}">
                <div class="success-message">${successMsg}</div>
            </c:if>

            <form action="register" method="post" onsubmit="return validateRegisterForm() && validatePhoneNumber()">

                <!-- Username -->
                <label>Username <span style="color: red;">*</span></label>
                <input name="username" type="text" placeholder="Username" value="${username != null ? username : ''}" required >

                <!-- Email -->
                <label>Email <span style="color: red;">*</span></label>
                <input name="email" type="email" placeholder="Email" value="${email != null ? email : ''}" required>

                <!-- Password -->
                <label>Password <span style="color: red;">*</span></label>
                <div class="password-container">
                    <input name="password" type="password" id="register-password" placeholder="Password" value="${password != null ? password : ''}" required>
                    <i class="fas fa-eye toggle-password" id="togglePassword"></i>
                </div>

                <!-- Confirm Password -->
                <label>Confirm Password <span style="color: red;">*</span></label>
                <div class="password-container">
                    <input name="repass" type="password" id="register-repass" placeholder="Confirm Password" value="${repass != null ? repass : ''}" required>
                    <i class="fas fa-eye toggle-password" id="toggleRePassword"></i>
                </div>

                <!-- Phone -->
                <label>Phone <span style="color: red;">*</span></label>
                <input name="phone" type="text" placeholder="Phone number" value="${phone != null ? phone : ''}" required>

                <!-- Full Name (Kh�ng b?t bu?c) -->
                <label>Full Name</label>
                <input name="fullname" type="text" placeholder="Full name" value="${fullname != null ? fullname : ''}">

                <!-- Address (Kh�ng b?t bu?c) -->
                <label>Address</label>
                <input name="address" type="text" placeholder="Address" value="${address != null ? address : ''}">

                <!-- Image (Kh�ng b?t bu?c) -->
                <label>Image URL</label>
                <input name="image" type="text" placeholder="Image URL" value="${image != null ? image : ''}">

                <span id="error-message" style="color: red;"></span><br>
                <button type="submit">Register</button>
            </form>

            <div style="text-align: center; margin-top: 10px;">
                <a href="Login.jsp">Already have an account? Login here</a>
            </div>
        </div>

        <!-- Script x? l� toggle m?t v� ki?m tra password -->
        <script>
            // Toggle password visibility
            function togglePasswordVisibility(toggleId, inputId) {
                const toggle = document.getElementById(toggleId);
                const input = document.getElementById(inputId);
                toggle.addEventListener("click", function () {
                    const type = input.getAttribute("type") === "password" ? "text" : "password";
                    input.setAttribute("type", type);
                    this.classList.toggle("fa-eye");
                    this.classList.toggle("fa-eye-slash");
                });
            }

            togglePasswordVisibility("togglePassword", "register-password");
            togglePasswordVisibility("toggleRePassword", "register-repass");

            // Ki?m tra kh?p password
            function validateRegisterForm() {
                const password = document.getElementById("register-password").value;
                const repass = document.getElementById("register-repass").value;
                const errorMessage = document.getElementById("error-message");
                if (password !== repass) {
                    errorMessage.innerText = "Passwords do not match!";
                    return false;
                }
                errorMessage.innerText = "";
                return true;
            }
            
            //Check validate Phone number
            function validatePhoneNumber() {
                let phone = document.getElementById("phone").value;
                let phoneRegex = /^0\d{9}$/; // Ch? ch?p nh?n 10 s? v� b?t ??u b?ng s? 0
                if (!phoneRegex.test(phone)) {
                    document.getElementById("error-message").textContent = "S? ?i?n tho?i kh�ng h?p l?! Ph?i c� 10 s?, b?t ??u b?ng 0.";
                    return false;
                }
                return true;
            }

        </script>
    </body>
</html>
