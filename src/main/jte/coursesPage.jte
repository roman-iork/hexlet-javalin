@import java.util.List
@param List<String> courses

@template.layout.site(content = @`
    <div class="p-4">
        <p>Choose appropriate courses</p>
        <p>Available courses are:</p>
        @for(var id : courses)
            <div>
                <a href="courses/${id}">Course number ${id}</a>
            </div>
        @endfor
    </div>
`
)