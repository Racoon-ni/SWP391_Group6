<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login / Registration Form</title>
        <link rel="stylesheet" href="css/login.css">

        <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="YOUR_INTEGRITY_HASH" crossorigin="anonymous" referrerpolicy="no-referrer" />

    </head>

    <body>
        <div class="container" id="container">

            <div class="form-container login-container">
                <c:if test="${not empty error}">
                    <div style="color: red; text-align: center; margin-bottom: 15px;">
                        ${error}
                    </div>
                </c:if>
                <form action="login" method="post" onsubmit="">
                    <h1>Login here.</h1>
                    <% String successMsg = (String) request.getAttribute("successMsg"); %>
                    <% if (successMsg != null) { %>
                    <p style="color: green;"><%= successMsg %></p>
                    <% } %>
                    <input name="username" type="text" id="username" placeholder="Username" required="" autofocus="" />

                    <input name="password" type="password" id="password" placeholder="Password" required="" />
<!--
                    <input type="password" name="currentPassword" class="form-control border-primary" required id="currentPassword">
                    <button class="btn btn-outline-secondary" type="button" id="toggleCurrentPassword">
                        <i class="fa fa-eye" aria-hidden="true"></i>
                    </button>-->

                    <div class="content">
                        <div class="checkbox">
                            <input type="checkbox" name="checkbox" id="checkbox" />
                            <label>Remember me</label>
                        </div>
                        <div class="pass-link">
                            <a href="forgotPassword.jsp">Forgot your password?</a>
                        </div>
                    </div>
                    <%
                    String error = request.getParameter("error");
                    if ("invalid".equals(error)) {
                    %>
                    <script>alert('Wrong username or password!');</script>
                    <%
                        } else if ("locked".equals(error)) {
                    %>
                    <script>alert('Your account has been locked, please contact admin to unlock!');</script>
                    <%
                        }
                    %>
                    <button type="submit">Login</button>
                    <span>or use your account</span>
                    <div class="social-contaniner">
                        <a href="#" class="social"><i class="lni lni-google"></i></a>
                    </div>
                </form>
            </div>

            <div class="overlay-container">
                <div class="overlay">
                    <div class="overlay-panel overlay-left">
                        <h1 class="title">Hello <br> friends</h1>
                        <p>if You have an account, login here and have fun</p>
                        <button class="ghost" id="login">Login
                            <i class="lni lni-arrow-left login"></i>
                        </button>
                    </div>
                    <div class="overlay-panel overlay-right">
                        <h1 class="title">Start your <br> journey now</h1>
                        <p>If you don't have an account yet, join us and start your journey.</p>
                        <a href="register.jsp">
                            <button class="ghost">Register
                                <i class="lni lni-arrow-right register"></i>
                            </button>
                        </a>
                    </div>
                </div>
            </div>

        </div>

        <script src="js/login.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script>
<<<<<<< HEAD
            function validateForm() {
                var username = document.getElementById("inputEmail").value;
                var password = document.getElementById("inputPassword").value;

                if (username.trim() === "" || password.trim() === "") {
                    alert("Please enter both username and password");
                    return false;
                }
                return true;
            }

            function toggleResetPswd(e) {
                e.preventDefault();
                $('#logreg-forms .form-signin').toggle();
                $('#logreg-forms .form-reset').toggle();
            }

            function toggleSignUp(e) {
                e.preventDefault();
                $('#logreg-forms .form-signin').toggle();
                $('#logreg-forms .form-signup').toggle();
            }

            $(() => {
                $('#logreg-forms #forgot_pswd').click(toggleResetPswd);
                $('#logreg-forms #cancel_reset').click(toggleResetPswd);
                $('#logreg-forms #btn-signup').click(toggleSignUp);
                $('#logreg-forms #cancel_signup').click(toggleSignUp);
            })

            document.getElementById('toggleCurrentPassword').addEventListener('click', function (e) {
                togglePassword('password', this);
            });
            function togglePassword(inputId, button) {
                var input = document.getElementById(inputId);
                var icon = button.querySelector('i');
                if (input.type === "password") {
                    input.type = "text";
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    input.type = "password";
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            }
=======
                        function validateForm() {
                            var username = document.getElementById("inputEmail").value;
                            var password = document.getElementById("inputPassword").value;

                            if (username.trim() === "" || password.trim() === "") {
                                alert("Please enter both username and password");
                                return false;
                            }
                            return true;
                        }

                        function toggleResetPswd(e) {
                            e.preventDefault();
                            $('#logreg-forms .form-signin').toggle();
                            $('#logreg-forms .form-reset').toggle();
                        }

                        function toggleSignUp(e) {
                            e.preventDefault();
                            $('#logreg-forms .form-signin').toggle();
                            $('#logreg-forms .form-signup').toggle();
                        }

                        $(() => {
                            $('#logreg-forms #forgot_pswd').click(toggleResetPswd);
                            $('#logreg-forms #cancel_reset').click(toggleResetPswd);
                            $('#logreg-forms #btn-signup').click(toggleSignUp);
                            $('#logreg-forms #cancel_signup').click(toggleSignUp);
                        })
>>>>>>> 5969258d2346294a4874948f9246419422533aa2
        </script>
    </body>

</html>