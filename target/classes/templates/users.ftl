<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Banners</title>

    <link rel="stylesheet" href="/assets/css/style.css">
</head>
<body>


<div class="images-wrapper">
        <img src="${userPhoto}" alt="image of game" class="image-to-show active" />
</div>
<p class="userName">
     <#if userName??>${userName}</#if>
</p>

<form action="/users" method="post" class="btn-wrapper">
    <input type="submit" name="boolean" class="btn-end btn" value="NO"/>
    <input type="submit"  name="boolean" class="btn-continue btn" value="YES"/>
</form>
</body>
</html>