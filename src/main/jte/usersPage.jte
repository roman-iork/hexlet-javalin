@import org.example.NamedRoutes
@import org.example.dto.DelUserPage
@import java.util.List

@param List<String> users
@param DelUserPage page

@template.layout.site(content = @`
    @if(page.getErrors() != null)
        @for(var validator : page.getErrors().values())
            @for(var error : validator)
                <p class="pt-3 ps-3" style="color: red">${error.getMessage()}</p>
            @endfor
        @endfor
    @endif
    <form action="${NamedRoutes.pathUsersDel()}" method="post" class="p-4 pb-2 pt-1">
        <label>
            <p>Deletion</p>
            <input type="text" name="firstName" required placeholder="first name" value="${page.getFirstName()}">
        </label>
        <input type="submit" value="Well, delete means delete...">
    </form>
    <div class="p-4">
        @if(!users.isEmpty())
            <div>
                @for(var name : users)
                    <p>${name}</p>
                @endfor
            </div>
        @endif
    </div>
`
)
