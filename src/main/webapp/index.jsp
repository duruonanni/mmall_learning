<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<body>
<h2>Hello World!</h2>

<h1>用户登录</h1>
<form action="/user/login.do" method="post">
    <input type="text" name="username" />
    <input type="text" name="password">
    <input type="submit" value="登录">
</form>

<h1>SpringMVC上传测试</h1>
<form action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="springMVC上传文件">
</form>
<hr>
<h1>富文本图片上传文件</h1>
<form action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本图片上传文件">
</form>
</body>
</html>
