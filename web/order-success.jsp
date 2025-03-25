<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt hàng thành công</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f0f8ff;
            padding: 40px;
            text-align: center;
        }

        .box {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 0 15px #ccc;
            display: inline-block;
        }

        h2 {
            color: #28a745;
            font-size: 32px;
            margin-bottom: 20px;
        }

        p {
            font-size: 18px;
            color: #333;
        }

        .btn {
            display: inline-block;
            margin-top: 25px;
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .btn:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>

    <div class="box">
        <h2>✅ Đặt hàng thành công!</h2>
        <p>Cảm ơn bạn đã mua hàng tại Online BookStore.</p>
        <p>Chúng tôi sẽ xử lý và giao đơn hàng trong thời gian sớm nhất.</p>
        <a href="orders" class="btn">📦 Xem đơn hàng của bạn</a>
    </div>

</body>
</html>
