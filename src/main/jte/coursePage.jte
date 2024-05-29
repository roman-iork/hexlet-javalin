@import org.example.Course

@param Course course

@template.layout.site(
    content = @`
        <div class="p-4">
            @if(course.getId().equals(1L))
            <p>Attention! Super important!</p>
            @endif
            <p>${course.getName()}</p>
            <p>${course.getBody()}</p>
        </div>
    `
)
