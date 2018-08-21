import com.intellij.json.JsonLanguage;
import com.intellij.json.JsonParserDefinition;
import com.intellij.json.psi.impl.JsonFileImpl;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class SimpleParserDefinition implements ParserDefinition {
    JsonParserDefinition parserDefinition = new JsonParserDefinition();
    public static final IFileElementType FILE = new IFileElementType(SimpleLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return parserDefinition.createLexer(project);
    }

    @Override
    public PsiParser createParser(Project project) {
        return parserDefinition.createParser(project);
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return parserDefinition.getCommentTokens();
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return parserDefinition.getStringLiteralElements();
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return parserDefinition.createElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new JsonFileImpl(viewProvider, SimpleLanguage.INSTANCE);
    }
}
