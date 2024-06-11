@import org.example.utils.NamedRoutes
@import org.example.dto.UserPage

@param UserPage page

@template.layout.site(
    content = @`
        <p>Here is a form for editing user called ${page.getUser().getFirstName()}</p>
        <form action="${NamedRoutes.pathUsers() + "/" + page.getUser().getId() + "/update"}" method="post">
            <label>
                First name
                <input type="text" name="firstName" value="${page.getUser().getFirstName()}">
            </label>
            <label>
                Email
                <input type="email" name="email" value="${page.getUser().getEmail()}">
            </label>
            <label>
                Password
                <input type="password" name="password" placeholder="the same if nothing typed">
            </label>
            <input type="submit">
        </form>
    `
)
