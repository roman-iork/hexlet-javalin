@import java.util.List

@param List<String> users

@template.layout.site(content = @`
    <form action="/users/del" method="post" class="p-4 pb-2">
        <label>
            <p>Deletion</p>
            <input type="text" name="firstName" required placeholder="first name">
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
