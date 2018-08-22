import com.intellij.lang.Language;

public class MacroLanguage extends Language {
    public static final MacroLanguage INSTANCE = new MacroLanguage();

    private MacroLanguage() {
        super("Macro");
    }
}