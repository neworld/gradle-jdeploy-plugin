package lt.neworld.gradle.jdeploy.task;

import com.moowork.gradle.node.npm.NpmTask;
import lt.neworld.gradle.jdeploy.JDeployExtensionKt;
import org.gradle.api.tasks.Input;

import java.util.Arrays;

/**
 * @author Andrius Semionovas
 * @since 2017-11-27
 */
public class JDeployPrepare extends NpmTask {
    public final static String NAME = "jdeployPrepare";

    public JDeployPrepare() {
        setArgs(Arrays.asList("install", "jdeploy@" + version()));
    }

    @Input
    public String version() {
        return JDeployExtensionKt.getJdeployExtension(getProject()).getOptions().getVersion();
    }
}
