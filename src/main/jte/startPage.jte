@param org.example.dto.MainPage page

@template.layout.site(
    content = @`
        <div class="p-4">
            <p>Hello!</p>
            <p>On this site you can get some info on some courses and users.</p>
            <p>You also can create courses and users and delete them.</p>
            <p>Just hit relevant link above 👆</p>
            <p>Enjoy 🏄</p>
        </div>
        <div>
            <p>If tou see some date and time under this line then cookies are set for this site</p>
            @if(page.getData() != null)
                <p class="ms-5">${page.getData()}</p>
            @endif
        </div>
    `
)