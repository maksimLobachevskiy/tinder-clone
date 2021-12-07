<html lang="en">
<head>
    <!-- Bootstrap core CSS -->
    <link href="/assets/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="/assets/style.css">
</head>

<body>
        <form action="/" method="get">
            Name: <input type="text" name="login" placeholder="enter login" class="form-control"/> <br/>
            Name: <input type="password" name="password" placeholder="enter password" class="form-control"/> <br/>
            <input type="submit" value="Ok"/>
        </form>

       <p>
            <#if user??>${user}</#if>
        </p>


    </body>
</html>