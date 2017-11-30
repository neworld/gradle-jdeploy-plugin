package lt.neworld.gradle.jdeploy.task;

import com.moowork.gradle.node.NodeExtension;
import com.moowork.gradle.node.npm.NpmTask;
import lt.neworld.gradle.jdeploy.JDeployExtensionKt;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;

import java.io.File;
import java.util.Arrays;

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

    @Override
    public void exec() {
        setArgs(Arrays.asList("install", "jdeploy@" + getVersion()));
        super.exec();
    }
}
