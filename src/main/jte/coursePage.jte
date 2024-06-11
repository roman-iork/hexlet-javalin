@import org.example.utils.NamedRoutes
@param org.example.dto.CoursePage page

@template.layout.site(
    content = @`
        <div>
            <p>${page.getCourse().getName()}</p>
            <p>${page.getCourse().getDescription()}</p>
            <p>${page.getCourse().getBody()}</p>
        </div>
        <form action="${NamedRoutes.pathCourseEdit(page.getCourse().getId())}" method="get">
            <input type="submit" value="Edit course">
        </form>
        <form action="${NamedRoutes.pathCoursesDel(page.getCourse().getId())}" method="post">
            <input type="submit" value="Delete course">
        </form>
    `
)