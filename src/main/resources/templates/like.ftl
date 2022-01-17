<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Banners</title>
    <link rel="stylesheet" href="/assets/style.css">
</head>
<body>

<#if users??><h1 class="like_title">You have liked these users! </h1></#if>

<input class="button-footer-form" type="button" value="Exit">

<div class="user_block">
    <#list users as user>
        <div class="user">
            <img class="user_img" src=${user.url} alt=`тут должно быть фото`/>
            <p class="user_capture">${user.name}</p>
        </div>
    </#list>
</div>


</body>
</html>
