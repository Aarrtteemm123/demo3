<#--<#import "/spring.ftl" as spring>-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List</title>
    <#--<link rel="stylesheet" href="<@spring.url '/css/style.css/'>">-->
    <style>
        body {
            background: #47e7db;
        }

        .inputForm {
            position: absolute; /* Абсолютное позиционирование */
            top: 200px;
            left: 510px;
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
            width: 150px;
            height: 20px;
            font-family: sans-serif, impact;
            font-size: 20px;
            font-weight: bold;
        }

        .tableButtonList {
            position: absolute; /* Абсолютное позиционирование */
            top: 80px;
            left: 185px;
            border: 2px solid black;
            border-spacing: 70px 11px;
            color: blueviolet;
            font-size: 20px;

        }

        .textTitle {
            position: absolute; /* Абсолютное позиционирование */
            left: 700px;
            top: 10px;
            color: darkred;
        }

        .buttonInput {
            position: absolute; /* Абсолютное позиционирование */
            left: 500px;
            top: 325px;
            height: 25px;
            width: 100px;
        }

        .table_dark {
            position: absolute; /* Абсолютное позиционирование */
            top: 380px;
            left: 500px;
            font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif, serif;
            font-size: 14px;
            text-align: left;
            border-collapse: collapse;
            background: #252F48;
            margin: 10px;
            border-radius: 10px;
        }

        .table_dark th {
            color: #EDB749;
            border-bottom: 1px solid #37B5A5;
            padding: 12px 17px;
        }

        .table_dark td {
            color: #CAD4D6;
            border-bottom: 1px solid #37B5A5;
            border-right: 1px solid #37B5A5;
            padding: 7px 17px;
        }

        .table_dark tr:last-child td {
            border-bottom: none;
        }

        .table_dark td:last-child {
            border-right: none;
        }

        .table_dark tr:hover td {
            text-decoration: underline;
        }

        input.button[type=submit] {
            position: relative;
            display: inline-block;
            height: 25px;
            width: 80px;
            line-height: 60%;
            font-size: 90%;
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
<h3 style="font-family:sans-serif,impact; color: darkred; position: absolute;left: 30px;top: 5px;">
    Status: ${status.status}</h3>
<h2><a href="/title"
       style="font-family:sans-serif,impact;color: darkred;position: absolute;right: 50px;top: 20px;text-decoration: none">Exit</a>
</h2>
<h2 class="textTitle">Tables</h2>
<table class="tableButtonList">
    <tr>
        <td><a href="/boardGames/list" style="color: blue;text-decoration: none">Board game</a></td>
        <td><a href="/court/list" style="color: blue;text-decoration: none">Court</a></td>
        <td><a href="/iceRink/list" style="color: blue;text-decoration: none">Ice rink</a></td>
        <td><a href="/manege/list" style="color: blue;text-decoration: none">Manege</a></td>
        <td><a href="/sportGym/list" style="color: blue;text-decoration: none">Sport gym</a></td>
        <td><a href="/stadium/list" style="color: blue;text-decoration: none">Stadium</a></td>
        <td><a href="/swimmingPool/list" style="color: blue;text-decoration: none">Swimming pool</a></td>
    </tr>
    <tr>
        <td><a href="/track/list" style="color: blue;text-decoration: none">Track</a></td>
        <td><a href="/trainer/list" style="color: blue;text-decoration: none">Trainer</a></td>
        <td><a href="/sport/list" style="color: blue;text-decoration: none">Sport</a></td>
        <td><a href="/sportsmen/list" style="color: blue;text-decoration: none">Sportsmen</a></td>
        <td><a href="/club/list" style="color: blue;text-decoration: none">Club</a></td>
        <td><a href="/organizesAndHistorySportsCompetitions/list" style="color: blue;text-decoration: none">History</a>
        </td>
        <td><a href="/queries/list" style="color: blue;text-decoration: none">Queries</a></td>
    </tr>
</table>
<a style="color: black; font-family:sans-serif,impact;position: absolute;left: 520px;top: 330px;" href="/iceRink/list">All
    ice rinks</a>
<a style="color: black; font-family:sans-serif,impact;position: absolute;left: 520px;top: 360px;" href="/iceRink/add">Add
    ice rink</a>
<form class="inputForm" action="/iceRink/list" method="POST">
    <div class="field">
        <label for="num">Square (<)</label>
        <input class="input" type="number" name="num" value="0">
    </div>
    <br>
    <input style="left: 220px;top: 10px;" class="button" type="submit" value="Ok">
</form>

<div class="table_dark">
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Address</th>
            <th>Square</th>
            <th>Delete</th>
            <th>Edit</th>
        </tr>
        <#list iceRink as tempIceRink>
            <tr>
                <td>${tempIceRink.id}</td>
                <td>${tempIceRink.name}</td>
                <td>${tempIceRink.address}</td>
                <td>${tempIceRink.square}</td>
                <td><a href="/iceRink/delete/${tempIceRink.id}" style="color: #47e7db;text-decoration: none">Delete</a>
                </td>
                <td>
                    <a href="/iceRink/edit/${tempIceRink.id}" style="color: #47e7db;text-decoration: none">Edit</a>
                </td>
            </tr>
        </#list>
    </table>
</div>
</body>
</html>