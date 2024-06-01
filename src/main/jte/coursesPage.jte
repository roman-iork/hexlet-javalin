@import java.util.List

@param List<String> courses

@template.layout.site(content = @`
    <form action="/courses/del" method="post" class="p-4 pb-2">
        <label>
            <p>Deletion</p>
            <input type="text" name="name" required placeholder="name">
        </label>
        <input type="submit" value="Well, delete the course!">
    </form>
    <div class="p-4">
        @if(!courses.isEmpty())
            <div>
                @for(var name : courses)
                    <p>${name}</p>
                @endfor
            </div>
        @endif
    </div>
`
)
