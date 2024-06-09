@import gg.jte.Content
@import org.example.NamedRoutes
@import org.example.dto.BasePage

@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>My site 👋</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
</head>
<body class="p-4">
@if(page != null && page.getFlash() != null)
    @if(page.getFlash().equals("User was successfully added"))
        <p style="background: #ccf7bc; font-size: 20px; text-align: center">${page.getFlash()}</p>
    @else
        <p style="background: #ffc58f; font-size: 20px; text-align: center">${page.getFlash()}</p>
    @endif
@endif
<header>
    <h2>Welcome to my site 🚲</h2>
    <a href="/" class="p-4">Main page</a>
    <a href="${NamedRoutes.pathUsers()}" class="p-4">Users</a>
    <a href="${NamedRoutes.pathCourses()}" class="p-4">Courses</a>
    <a href="${NamedRoutes.pathUsersNew()}" class="p-4">Register user</a>
    <a href="${NamedRoutes.pathCoursesNew()}" class="p-4">Create course</a>
    <a href="${NamedRoutes.pathSessionBuildMain()}" class="p-4">Log in</a>
    <a href="${NamedRoutes.pathSessionOut()}" class="p-4">Log out</a>
</header>
<main>
    ${content}
</main>
<footer>
    <a href="https://github.com/roman-iork">There you can check my GitHub page 👈</a>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>
