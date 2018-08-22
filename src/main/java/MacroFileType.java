import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MacroFileType extends LanguageFileType {
    public static final Icon FILE = IconLoader.getIcon("/template.png");
    public static final MacroFileType INSTANCE = new MacroFileType();

    private MacroFileType() {
        super(MacroLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Macro file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Macro language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "macro";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FILE;
    }
}