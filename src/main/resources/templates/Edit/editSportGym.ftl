<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit</title>
    <style>
        body {
            background: #63c86c;
        }

        .form {
            position: absolute; /* Абсолютное позиционирование */
            left: 490px;
            top: 160px;
        }

        .input.input {
            border-radius: 7px;
            font-family: sans-serif, impact;
            font-size: 18px;
            color: black;
            background: lightgrey;
            height: 30px;
            box-shadow: 0 0 8px rgba(0, 0, 0, 0.5);
        }

        div.field {
            padding-bottom: 1px;
        }

        div.field label {
            display: block;
            float: left;
            width: 180px;
            height: 20px;
            font-family: sans-serif, impact;
            font-size: 20px;
            font-weight: bold;
        }

        input.button[type=submit] {
            position: relative;
            display: inline-block;
            height: 30px;
            width: 100px;
            line-height: 52%;
            font-size: 100%;
            font-weight: 700;
            color: rgb(209, 209, 217);
            text-decoration: none;
            text-shadow: 0 -1px 2px rgba(0, 0, 0, .2);
            padding: .5em 1em;
            outline: none;
            border-radius: 3px;
            background: linear-gradient(rgb(110, 112, 120), rgb(81, 81, 86)) rgb(110, 112, 120);
            box-shadow: 0 1px rgba(255, 255, 255, .2) inset,
            0 3px 5px rgba(0, 1, 6, .5),
            0 0 1px 1px rgba(0, 1, 6, .2);
            transition: .2s ease-in-out;
        }

        input.button:hover:not(:active) {
            background: linear-gradient(rgb(126, 126, 134), rgb(70, 71, 76)) rgb(126, 126, 134);
        }

        input.button:active {
            top: 1px;
            background: linear-gradient(rgb(76, 77, 82), rgb(56, 57, 62)) rgb(76, 77, 82);
            box-shadow: 0 0 1px rgba(0, 0, 0, .5) inset,
            0 2px 3px rgba(0, 0, 0, .5) inset,
            0 1px 1px rgba(255, 255, 255, .1);
        }
    </style>
</head>
<body>
<h1 style="position: absolute;left: 676px;top: 30px;">Edit sport gym</h1>
<form class="form" action="/sportGym/edit" method="POST">
    <div class="field">
        <label for="name">Name</label>
        <input class="input" type="text" name="name" value="${sportGym.name}">
    </div>
    <br>
    <div class="field">
        <label for="address">Address</label>
        <input class="input" type="text" name="address" value="${sportGym.address}">
    </div>
    <br>
    <div class="field">
        <label for="numberOfSimulators">Number of simulators</label>
        <input class="input" type="text" pattern="^[0-9]+$" name="numberOfSimulators"
               value="${sportGym.numberOfSimulators}">
    </div>
    <br>
    <input style="left: 243px;top: 30px" class="button" type="submit" value="Save">
</form>
</body>
</html>