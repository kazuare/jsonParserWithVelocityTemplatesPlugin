import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class SimpleFileType extends LanguageFileType {
    public static final Icon FILE = IconLoader.getIcon("/jsonWithTemplates.png");
    public static final SimpleFileType INSTANCE = new SimpleFileType();

    private SimpleFileType() {
        super(SimpleLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Simple file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Simple language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "simple";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FILE;
    }
}