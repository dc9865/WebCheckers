<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title} </title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">
    <div class="error">
        <#include "message.ftl" />
    </div>
    <form action="./postsignin" method="POST">
        Username:
        <br/>
        <input type="text" name="Username"/>
        <br/>
        <button type="submit">Submit</button>
    </form>
</div>
</body>

</html>