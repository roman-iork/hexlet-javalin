@import org.example.utils.NamedRoutes
@import org.example.dto.CoursePage

@param CoursePage page
@template.layout.site(
    content = @`
        <p>Here you can edit course</p>
        <form action="${NamedRoutes.pathCourseUpdate(page.getCourse().getId())}" method="post">
            <input type="text" name="name" placeholder="name" value="${page.getCourse().getName()}">
            <textarea name="description" rows="5">${page.getCourse().getDescription()}</textarea>
            <textarea name="body" rows="10">${page.getCourse().getBody()}</textarea>
            <input type="submit" value="Edit">
        </form>
    `
)
