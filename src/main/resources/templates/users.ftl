<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Banners</title>
    <link rel="stylesheet" href="/assets/style.css">
    <link rel="stylesheet" href="/assets/bootstrap.min.css">
</head>
<body>


<div class="images-wrapper">
        <img src="${userPhoto}" alt="image of game" class="image-to-show active" />
</div>
<p class="userName">
     <#if userName??>${userName}</#if>
</p>

<form action="/users" method="get" class="btn-wrapper">
    <input type="submit" name="boolean" class="btn-end btn" value="NO"/>
    <input type="submit"  name="boolean" class="btn-continue btn" value="YES"/>
</form>

</body>
</html>