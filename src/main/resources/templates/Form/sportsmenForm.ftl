<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add</title>
    <style>
        body {
            background: #e7a064;
        }

        .form {
            position: absolute; /* Абсолютное позиционирование */
            left: 480px;
            top: 150px;
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
<h1 style="position: absolute;left: 665px;top: 30px;">Add sportsmen</h1>
<form class="form" action="/sportsmen/add" method="POST">
    <div class="field">
        <label for="id">Id</label>
        <input class="input" type="text" pattern="^[0-9]+$" name="id" value="0">
    </div>
    <br>
    <div class="field">
        <label for="name">Name</label>
        <input class="input" type="text" name="name">
    </div>
    <br>
    <div class="field">
        <label for="personalKey">Personal key</label>
        <input class="input" type="text" pattern="^[0-9]+$" name="personalKey" value="0">
    </div>
    <br>
    <div class="field">
        <label for="sport">Sport</label>
        <input class="input" type="text" name="sport">
    </div>
    <br>
    <div class="field">
        <label for="sportCategory">Sport category</label>
        <input class="input" type="text" name="sportCategory">
    </div>
    <br>
    <div class="field">
        <label for="trainerName">Name trainer</label>
        <input class="input" type="text" name="trainerName">
    </div>
    <br>
    <div class="field">
        <label for="trainerId">Trainer id</label>
        <input class="input" type="text" pattern="^[0-9]+$" name="trainerId" value="0">
    </div>
    <br>
    <div class="field">
        <label for="clubName">Name club</label>
        <input class="input" type="text" name="clubName">
    </div>
    <br>
    <div class="field">
        <label for="numberOfParticipation">Number of participation</label>
        <input class="input" type="text" pattern="^[0-9]+$" name="numberOfParticipation" value="0">
    </div>
    <br>
    <input style="left: 240px;top: 20px" class="button" type="submit" value="Save">
</form>
</body>
</html>