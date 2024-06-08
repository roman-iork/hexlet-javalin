@import org.example.NamedRoutes
@import org.example.dto.LoginPage

@param LoginPage page

@template.layout.site(
    content = @`
        @if(page.getErrors() != null)
            @for(var validation : page.getErrors().values())
                @for(var error : validation)
                    <p>${error.getMessage()}</p>
                @endfor
            @endfor
        @endif
        <form action="${NamedRoutes.pathSessionCheck()}" method="post">
            <input type="text" name="firstName" placeholder="First name" value="${page.getFirstName()}">
            <input type="password" name="password" placeholder="Password">
            <input type="submit" value="Let's try">
        </form>
    `
)