package lt.neworld.gradle.jdeploy.task;

import com.moowork.gradle.node.NodeExtension;
import com.moowork.gradle.node.npm.NpmTask;
import lt.neworld.gradle.jdeploy.JDeployExtensionKt;
import lt.neworld.gradle.jdeploy.ToolOptions;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrius Semionovas
 * @since 2017-11-27
 */
public class JDeploySetup extends NpmTask {
    public final static String NAME = "jdeploySetup";

    @Input
    public String getVersion() {
        return JDeployExtensionKt.getJdeployExtension(getProject()).getOptions().getToolVersion();
    }

    @OutputDirectory
    public File getJdeployOutput() {
        return new File(NodeExtension.get(getProject()).getNodeModulesDir(), "jdeploy");
    }

    private NodeExtension getNodeExtension() {
        return NodeExtension.get(getProject());
    }

    private ToolOptions getToolOptions() {
        return JDeployExtensionKt.getJdeployExtension(getProject()).getOptions();
    }

    @Input
    public boolean getNodeDownload() {
        return getNodeExtension().getDownload();
    }

    @Input
    public boolean getAllowGlobalInstall() {
        return getToolOptions().getAllowGlobalInstall();
    }

    @Override
    public void exec() {
        if (!getNodeDownload() && !getAllowGlobalInstall()) {
            throw new IllegalArgumentException("You are using global npm." +
                    "You have to explicit let plugin install jdeploy globally:\n" +
                    "\n" +
                    "jdeploy {\n" +
                    "  options {\n" +
                    "    allowGlobalInstall = true\n" +
                    "  }\n" +
                    "}\n" +
                    "\n" +
                    "or enable local copy of npm:\n" +
                    "\n" +
                    "node {\n" +
                    "  download = true\n" +
                    "}"
            );
        }


        List<String> args;
        if (!getNodeDownload()) {
            args = Arrays.asList("install", "jdeploy@" + getVersion(), "-g");
        } else {
            args = Arrays.asList("install", "jdeploy@" + getVersion());
        }
        setArgs(args);
        super.exec();
    }
}
