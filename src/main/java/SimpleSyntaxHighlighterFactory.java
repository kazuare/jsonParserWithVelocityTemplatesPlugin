import com.intellij.json.JsonLanguage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class SimpleSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
    @NotNull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
        Logger logger = Logger.getInstance(SimpleSyntaxHighlighterFactory.class);
        logger.info("---> " + convertStreamToString(virtualFile));
        return SyntaxHighlighterFactory.getSyntaxHighlighter(JsonLanguage.INSTANCE, project, virtualFile);
    }

    static String convertStreamToString(VirtualFile virtualFile) {
        try {
            java.util.Scanner s = new java.util.Scanner(virtualFile.getInputStream()).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
