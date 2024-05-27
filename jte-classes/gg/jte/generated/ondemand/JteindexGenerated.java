package gg.jte.generated.ondemand;
import org.example.Course;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,12,12,12,14,14,15,15,15,16,16,16,17,22,22,22,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Course course) {
		jteOutput.writeContent("\n<!doctype html>\n<html>\n    <head>\n        <meta charset=\"utf-8\" />\n        <title>Yo fellows!</title>\n    </head>\n    <body>\n        <div>\n            <main>\n                ");
		if (course.getId().equals(1L)) {
			jteOutput.writeContent("\n                    <p>Attention! Super important!</p>\n                ");
		}
		jteOutput.writeContent("\n                <p>");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(course.getName());
		jteOutput.writeContent("</p>\n                <p>");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(course.getBody());
		jteOutput.writeContent("</p>\n");
		jteOutput.writeContent("\n            </main>\n        </div>\n    </body>\n</html>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Course course = (Course)params.get("course");
		render(jteOutput, jteHtmlInterceptor, course);
	}
}
