<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="main.css" rel="stylesheet">
    <style>
        body{
            background: #929292;
        }

        .form
        {
            position: absolute; /* Абсолютное позиционирование */
            left: 445px;
            top: 200px;
        }
        .titleText {
            position: absolute; /* Абсолютное позиционирование */
            left: 380px;
            top: 40px;
            font-family: sans-serif,impact;
            font-size: 40px;
        }

        input.button[type=submit] {
            position: relative;
            display: inline-block;
            line-height: 75%;
            font-size: 90%;
            font-weight: 700;
            color: rgb(209,209,217);
            text-decoration: none;
            text-shadow: 0 -1px 2px rgba(0,0,0,.2);
            padding: .5em 1em;
            outline: none;
            border-radius: 3px;
            background: linear-gradient(rgb(110,112,120), rgb(81,81,86)) rgb(110,112,120);
            box-shadow:
                    0 1px rgba(255,255,255,.2) inset,
                    0 3px 5px rgba(0,1,6,.5),
                    0 0 1px 1px rgba(0,1,6,.2);
            transition: .2s ease-in-out;
        }
        input.button:hover:not(:active) {
            background: linear-gradient(rgb(126,126,134), rgb(70,71,76)) rgb(126,126,134);
        }
        input.button:active {
            top: 1px;
            background: linear-gradient(rgb(76,77,82), rgb(56,57,62)) rgb(76,77,82);
            box-shadow:
                    0 0 1px rgba(0,0,0,.5) inset,
                    0 2px 3px rgba(0,0,0,.5) inset,
                    0 1px 1px rgba(255,255,255,.1);
        }
        .input.input
        {
            border-radius: 7px;
            font-family: sans-serif,impact;
            font-size: 18px;
            color: black;
            background: lightgrey;
            height: 30px;
            box-shadow: 0 0 8px rgba(0,0,0,0.5);
        }
        div.field {
            padding-bottom: 1px;
        }

        div.field label {
            display: block;
            float: left;
            width: 180px;
            height: 20px;
            font-family: sans-serif,impact;
            font-size: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h1 class="titleText">Information system of sports organizations</h1>
<a style="color: black; font-family:sans-serif,impact;position: absolute;left: 0px;top: 380px;" href="/task10">Task10</a>
<form class="form" action="/title" method="POST">
    <div class="field">
    <label for="string">USER :</label>
    <input class="input" type="text" maxlength="25" size="30" name="string" value="">
    </div>
    <br>
    <div class="field">
    <label for="string2">PASSWORD :</label>
    <input class="input" type="password" maxlength="25" size="30" name="string2" value="">
    </div>
    <br>
    <input style="position: absolute;left: 260px; top: 150px; height: 35px;width: 150px;" class="button" type="submit" value="OK">
</form>
</body>
</html>