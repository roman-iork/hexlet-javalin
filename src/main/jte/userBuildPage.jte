@import io.javalin.validation.ValidationError
@import org.example.dto.BuildUserPage
@import java.util.List

@param BuildUserPage page

@template.layout.site(content = @`
    @if(page.getErrors() != null)
        @for(List<ValidationError<Object>> validator : page.getErrors().values())
            @for(ValidationError<Object> error : validator)
                <p class="pt-3 ps-5" style="color: red">${error.getMessage()}</p>
            @endfor
        @endfor
    @endif
    <div class="p-4 pt-0">
        <p>Here you can register a new user:</p>
        <form action="/users" method="post" class="pb-3">
            <label>
                <p>Name</p>
                <input type="text" name="firstName" required placeholder="first name" value="${page.getFirst_name()}">
            </label>
            <label>
                <p>Email</p>
                <input type="email" name="email" required placeholder="email" value="${page.getEmail()}">
            </label>
            <label>
                <p>Password</p>
                <input type="password" name="password" required placeholder="password">
            </label>
            <label>
                <p>Confirm password</p>
                <input type="password" name="passwordConfirmation" required placeholder="confirm password">
            </label>
            <input type="submit" value="Do it, register!">
        </form>
    </div>
`
)
