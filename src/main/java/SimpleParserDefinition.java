import com.intellij.json.JsonParserDefinition;
import com.intellij.json.psi.impl.JsonFileImpl;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.SimpleLog4JLogSystem;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class SimpleParserDefinition implements ParserDefinition {
    JsonParserDefinition parserDefinition = new JsonParserDefinition();
    public static final IFileElementType FILE = new IFileElementType(SimpleLanguage.INSTANCE);
    Logger logger = Logger.getInstance(SimpleParserDefinition.class);

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
        Path jsonFileFolder = Paths.get(viewProvider.getVirtualFile().getPath()).getParent();
        String jsonFileContent = viewProvider.getContents().toString();

        logger.info("Starting json ---> " + jsonFileContent);

        VelocityEngine velocity = getVelocity();
        StringResourceRepository repo = StringResourceLoader.getRepository();

        for (VirtualFile file : getAllMacroFiles(viewProvider.getManager().getProject())) {
            Path macrosFile = Paths.get(file.getPath());

            logger.info("Trying to figure out relative path from paths: " + jsonFileFolder + " - " + macrosFile);

            String templateName = jsonFileFolder.relativize(macrosFile).toString();
            try {
                String template = new String(file.contentsToByteArray());
                repo.putStringResource(templateName.replace("\\", "/"), template);
                repo.putStringResource(templateName.replace("/", "\\"), template);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StringWriter stringWriter = new StringWriter();
        velocity.evaluate(new VelocityContext(), stringWriter, "GenerateToString", jsonFileContent);
        logger.info("Resulting json:---> " + stringWriter.toString());

        return new JsonFileImpl(viewProvider, SimpleLanguage.INSTANCE);
    }

    private Collection<VirtualFile> getAllMacroFiles(Project project) {
        return FileTypeIndex.getFiles(
                MacroFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
    }

    private VelocityEngine getVelocity(){
        ExtendedProperties prop = new ExtendedProperties();
        prop.addProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, SimpleLog4JLogSystem.class.getName());
        prop.addProperty("runtime.log.logsystem.log4j.category", "GenerateToString");
        prop.addProperty(RuntimeConstants.RESOURCE_LOADER, "string");
        prop.addProperty("string.resource.loader.description", "Velocity StringResource loader");
        prop.addProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        VelocityEngine velocity = new VelocityEngine();
        velocity.setExtendedProperties(prop);
        velocity.init();
        return velocity;
    }
}
