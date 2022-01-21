<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Banners</title>
    <link rel="stylesheet" href="/assets/style.css">
    <link rel="stylesheet" href="/assets/bootstrap.min.css">
</head>
<body>
<header>
    <img class="mb-4 logo"  src="https://cdn.worldvectorlogo.com/logos/tinder-2.svg" alt="" width="72" height="72">
</header>

<#if users??><h1 class="like_title">You have liked these users! </h1></#if>

<div>
    <a class="button-footer-form" href="/logout" type="button" value="Exit">Exit</a>
</div>


<div class="user_block">
    <div class="row gx-5">
    <#list users as user>

            <div class="col-6 mt-5">
        <div class="user">
            <img class="user_img" src=${user.url} alt=`тут должно быть фото`/>
            <p class="user_capture">${user.name}</p>
        </div>
        <div class="mt-2"> <form method="post">
                <button class="btn btn-danger btn-lg" type="submit">Send message</button>
                <input type="hidden" name="name" value="${user.id}">
            </form>
        </div>
            </div>

    </#list>
    </div>
</div>


</body>
</html>
