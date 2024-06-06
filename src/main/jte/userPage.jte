@import org.example.NamedRoutes
@import org.example.dto.UserPage

@param UserPage page

@template.layout.site(
    content = @`
        <div class="p-4">
            <p>${page.getUser().getFirstName()}</p>
            <p>${page.getUser().getEmail()}</p>
        </div>
        <div>
            <form action="${NamedRoutes.pathUsers() + "/" + page.getUser().getFirstName() + "/del"}" method="post">
                <input type="submit" value="Delete user">
            </form>
        </div>
        <div>
            <form action="${NamedRoutes.pathUsers() + "/" + page.getUser().getFirstName() + "/edit"}" method="get">
                <input type="submit" value="Edit user">
            </form>
        </div>
    `
)
