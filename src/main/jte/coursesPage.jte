@import org.example.NamedRoutes
@import org.example.dto.CoursesPage

@param CoursesPage page

@template.layout.site(content = @`
    <div class="p-4">
        @if(!page.getCourses().isEmpty())
            <div>
                @for(var course : page.getCourses())
                    <p>
                        <a href="${NamedRoutes.pathCourse(course.getId())}">${course.getName()}</a>
                    </p>
                @endfor
            </div>
        @endif
    </div>
`
)
