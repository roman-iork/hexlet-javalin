@import org.example.User

@param User user

@template.layout.site(
    content = @`
        <div class="p-4">
            @if(user.getId().equals(1L))
            <p>Attention! Super important!</p>
            @endif
            <p>${user.getFirstName()}</p>
            <p>${user.getEmail()}</p>
        </div>
    `
)
