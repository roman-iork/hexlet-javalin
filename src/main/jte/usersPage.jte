@import org.example.NamedRoutes
@import org.example.dto.UsersPage

@param UsersPage users

@template.layout.site(content = @`
    <div class="p-4">
        @if(!users.getUsers().isEmpty())
            <div>
                @for(var name : users.getUsers())
                    <p>
                        <a href="${NamedRoutes.pathUsers() + "/" + name}">${name}</a>
                    </p>
                @endfor
            </div>
        @endif
    </div>
`
)
