<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Banners</title>
    <style>
        .image-to-show {
            width: 400px;
            height: 400px;
            display: none;

        }
        .active{
            display: block;
        }
        .images-wrapper {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            align-items: start;
            margin-bottom: 40px;
        }
        .btn{
            padding: 20px 130px;
            border-radius: 4px;
            text-decoration: none;
            border-color: black;

        }
        .btn-end{margin-right: 40px;
            background-color: black;
            color: white;
        }
        .btn-continue{
            background-color: whitesmoke;
            color: black;
        }
        .btn-wrapper{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        p{text-align: center;}
    </style>
</head>
<body>


<div class="images-wrapper">
        <img src="${userPhoto}" alt="image of game" class="image-to-show active" />
</div>
<p>
     <#if userName??>${userName}</#if>
</p>

<form action="/users" method="post" class="btn-wrapper">
    <input type="submit" name="boolean" class="btn-end btn" value="NO"/>
    <input type="submit"  name="boolean" class="btn-end continue" value="YES"/>
</form>
</body>
</html>