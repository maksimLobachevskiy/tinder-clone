
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Banners</title>

</head>
<body>

<#if users??><h1>You have liked these users: </h1></#if>

<#list users as user>
<p>${user}
    </#list>

</body>
</html>
