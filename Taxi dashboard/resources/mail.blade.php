<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h3>Complains</h3>
    @foreach ($complains as $complain)
    <ul>
        <li><h6>{{$complain->txt}}</h6></li>
    </ul>
    @endforeach
</body>
</html>