@import org.example.Course
@param Course course

<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Yo fellows!</title>
    </head>
    <body>
        <div>
            <main>
                @if(course.getId().equals(1L))
                    <p>Attention! Super important!</p>
                @endif
                <p>${course.getName()}</p>
                <p>${course.getBody()}</p>
<%--                <p>${course.getId()}</p>--%>
            </main>
        </div>
    </body>
</html>
