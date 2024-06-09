@import org.example.NamedRoutes
@import org.example.dto.UsersPage

@param UsersPage page

@template.layout.site(
    page = page,
    content = @`
        <div class="p-4">
            @if(!page.getUsers().isEmpty())
                <div>
                    @for(var user : page.getUsers())
                        <p>
                            <a href="${NamedRoutes.pathUsers() + "/" + user.getId()}">${user.getFirstName()}</a>
                        </p>
                    @endfor
                </div>
            @endif
        </div>
    `
)
