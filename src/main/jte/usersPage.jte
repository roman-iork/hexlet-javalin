@import org.example.NamedRoutes
@import org.example.dto.UsersPage

@param UsersPage users

@template.layout.site(content = @`
    <div class="p-4">
        @if(!users.getUsers().isEmpty())
            <div>
                @for(var user : users.getUsers())
                    <p>
                        <a href="${NamedRoutes.pathUsers() + "/" + user.getId()}">${user.getFirstName()}</a>
                    </p>
                @endfor
            </div>
        @endif
    </div>
`
)
