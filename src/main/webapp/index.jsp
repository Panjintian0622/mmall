<%@ page language="java"  contentType="text/html; charset=UTF-8" %>
<html>
<head>
</head>
<body>
<h2>Hello World!</h2>
springMvc
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upl_file" />
    <input type="submit" value="springmvc上传文件" />
</form>
<%--<form name="form2" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">

    <input type="file",name="upload_file"/>
    <input type="submit" value="11" />
</form>--%>
</body>
</html>
