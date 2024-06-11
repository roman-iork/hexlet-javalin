@import org.example.utils.NamedRoutes
@import org.example.dto.CoursesPage

@param CoursesPage page

@template.layout.site(
    page = page,
    content = @`
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
