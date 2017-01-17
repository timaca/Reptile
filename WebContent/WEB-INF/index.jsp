<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
    <div align="center">
        <center>
        您的IP是：120.239.139.195
        来自：广东 湛江 移动 524000 0759
        <br>
        <a href="#"target="_blank">UserAgent</a>
        <
        </center>
    </div>
</body>
	
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script>
    $(document).ready(function(){
         var pattern = /(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)/;
       
        var str = document.getElementsByTagName("center")[0].innerHTML;
        var reg = new RegExp(pattern, "g");
        alert(str.match(reg));
       // alert(pattern.match(str));
    }); 
    
    
   
</script>
</html>