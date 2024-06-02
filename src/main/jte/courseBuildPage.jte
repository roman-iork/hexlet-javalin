@import org.example.NamedRoutes
@import org.example.dto.BuildCoursePage

@param BuildCoursePage page

@template.layout.site(content = @`
    @if(page.getErrors() != null)
        @for(var validator : page.getErrors().values())
            @for(var error : validator)
                <p class="pt-3 ps-5" style="color: red">${error.getMessage()}</p>
            @endfor
        @endfor
    @endif
    <div class="p-4 pt-0">
        <p>Voila ici vous pouves creer une cours neuveux:</p>
        <form action="${NamedRoutes.pathCourses()}" method="post" class="pb-3">
            <label>
                <p>Name</p>
                <input type="text" name="name" required placeholder="name" value="${page.getName()}">
            </label>
            <label>
                <p>Description</p>
                <input type="text" name="description" placeholder="description" value="${page.getDescription()}">
            </label>
            <label>
                <p>Body</p>
                <input type="text" name="body" required placeholder="course body" value="${page.getBody()}">
            </label>
            <input type="submit" value="Oh-la-la, la course neuveau!">
        </form>
    </div>
`
)
