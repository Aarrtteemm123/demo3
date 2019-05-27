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

        .table_dark {
            position: absolute; /* Абсолютное позиционирование */
            top: 300px;
            left: 570px;
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
<a style="color: black; font-family:sans-serif,impact;position: absolute;left: 590px;top: 280px;" href="/sport/add">Add
    sport</a>
<div class="table_dark">
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Delete</th>
            <th>Edit</th>
        </tr>
        <#list sports as tempSport>
            <tr>
                <td>${tempSport.id}</td>
                <td>${tempSport.name}</td>
                <td><a href="/sport/delete/${tempSport.id}" style="color: #47e7db;text-decoration: none">Delete</a></td>
                <td>
                    <a href="/sport/edit/${tempSport.id}" style="color: #47e7db;text-decoration: none">Edit</a>
                </td>
            </tr>
        </#list>
    </table>
</div>
</body>
</html>