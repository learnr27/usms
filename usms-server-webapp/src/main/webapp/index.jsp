<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>Oauth2 Server 端流程</title>
    <style type="text/css" rel="stylesheet">
        body {
            background-color: black;
            color: white;
            font-size: 18px;
            font-family: "Consolas";
            margin: 20px;
        }

        h1 {
            text-align: center;
        }

        a {
            color: white;
            font-size: 18px;
            cursor: pointer;
            text-decoration: none;
        }

        div {
            float: right;
        }
    </style>
</head>
<body>
<h1>Oauth2 Server 端流程</h1>
<div>
    <a href="${pageContext.request.contextPath}/client">应用管理</a>
    <a href="${pageContext.request.contextPath}/user">用户管理</a>
</div>
<pre>
0. 添加用户
http://192.168.200.209:8080/USMS/user

1. 先注册应用

http://192.168.200.209:8080/USMS/client

会生成client_id和client_secret 这两个以后会用到

client_id               0a626c2d-d0ac-45bd-9918-e66a4117cf4d    应用id
client_secret           c7569626-05be-41ee-ae6f-a69fdcb3b7eb    应用secret

2. 请求授权码

http://192.168.200.209:8080/USMS/authorize?client_id=0a626c2d-d0ac-45bd-9918-e66a4117cf4d&response_type=code&redirect_uri=http://www.baidu.com

参数说明

client_id               应用id
response_type           返回授权码的标识
redirect_uri            回调地址

上面的网站会打开oauth server的用户登录页面。用户输入正确的用户名和密码以POST方式提交后会重定向到用户所填的回调地址并在地址后携带授权码.

请求成功后会返回如下的页面:

http://www.baidu.com/?code=63910432da9186b22b1ad888d55ae8ae

这里code=63910432da9186b22b1ad888d55ae8ae 即授权码

3. 换取accessToken (POST操作)

http://192.168.200.209:8080/USMS/access
首先GET方式请求页面，会打开一个表单在该表单中填入必填项，具体表单参数详见说明部分

表单将会以POST方式提交到http://192.168.200.209:8080/USMS/accessToken,最终返回accessToken

需要以POST方式提交以下参数换取accessToken

client_id       c1ebe466-1cdc-4bd3-ab69-77c3561b9dee            应用id
client_secret   d8346ea2-6017-43ed-ad68-19c0f971738b            应用secret
grant_type      authorization_code                              用于传递授权码的参数名authorization_code
code            63910432da9186b22b1ad888d55ae8ae                用户登录授权后的授权码
redirect_uri    http://www.baidu.com                            回调地址

最终返回如下数据

{"expires_in":3600,"access_token":"223ae05dfbb0794396fb60a0960c197e"}

4. 测试accessToken

http://192.168.200.209:8080/USMS/v1/openapi/userInfo?access_token=223ae05dfbb0794396fb60a0960c197e

测试ok的话返回用户名信息，access_token=223ae05dfbb0794396fb60a0960c197e为上一步获取的access_token

注：其中的参数名不要随意更改，固定写法。
</pre>
</body>
</html>