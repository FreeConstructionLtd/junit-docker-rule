package pl.domzal.junit.docker.rule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DockerRuleBuiler {

    private String imageName;
    private List<String> binds = new ArrayList<>();;
    private String[] cmd;
    private String[] extraHosts;
    private String waitForMessage;
    private boolean keepContainer = false;
    private boolean imageAlwaysPull = false;

    DockerRuleBuiler(){}

    public DockerRule build() {
        return new DockerRule(this);
    }

    private static String[] nullToEmpty(String[] value) {
        return value==null ? new String[0] : value;
    }

    /**
     * Command to execute on container.
     */
    public DockerRuleBuiler cmd(String... cmd) {
        this.cmd = cmd;
        return this;
    }
    public String[] cmd() {
        return nullToEmpty(cmd);
    }

    /**
     * Image name to be used (required).
     */
    public DockerRuleBuiler imageName(String imageName) {
        this.imageName = imageName;
        return this;
    }
    public String imageName() {
        if (StringUtils.isEmpty(imageName)) {
            throw new IllegalStateException("imageName cannot be empty");
        }
        return imageName;
    }

    /**
     * Add extra host definitions into containers <code>/etc/hosts</code>.
     * @param extraHosts List of host matching format "hostname:address" (like desribed for 'docker run --add-host').
     */
    public DockerRuleBuiler extraHosts(String... extraHosts) {
        this.extraHosts = extraHosts;
        return this;
    }
    public String[] extraHosts() {
        return nullToEmpty(extraHosts);
    }

    /**
     * Make rule to wait for specified text in log on container start.
     */
    public DockerRuleBuiler waitForMessage(String waitForMessage) {
        this.waitForMessage = waitForMessage;
        return this;
    }
    public String waitForMessage() {
        return waitForMessage;
    }

    /**
     * Keep stopped container after test.
     */
    public DockerRuleBuiler keepContainer(boolean keepContainer) {
        this.keepContainer = keepContainer;
        return this;
    }
    public boolean keepContainer() {
        return keepContainer;
    }

    /**
     * Force image pull even when image is already present.
     */
    public DockerRuleBuiler imageAlwaysPull(boolean alwaysPull) {
        this.imageAlwaysPull = alwaysPull;
        return this;
    }
    public boolean imageAlwaysPull() {
        return imageAlwaysPull;
    }

    /**
     * Host directory to be mounted into container.<br/>
     * Please note that in boot2docker environments (OSX or Windows)
     * only locations inside $HOME can work (/Users or /c/Users respectively).<br/>
     * On Windows it is safer to use {@link #mountFrom(File)} instead.
     *
     * @param hostPath Directory to be mounted - must be specified Unix style.
     */
    public DockerRuleMountBuilderTo mountFrom(String hostPath) throws InvalidVolumeFrom {
        return new DockerRuleMountBuilder(this, hostPath);
    }
    /**
     * Host directory to be mounted into container.<br/>
     * Please note that in boot2docker environments (OSX or Windows)
     * only locations inside $HOME can work (/Users or /c/Users respectively).
     *
     * @param hostDir Directory to be mounted.
     */
    public DockerRuleMountBuilderTo mountFrom(File hostDir) throws InvalidVolumeFrom {
        String hostDirUnixPath = DockerRuleMountBuilder.toUnixStylePath(hostDir.getAbsolutePath());
        return new DockerRuleMountBuilder(this, hostDirUnixPath);
    }
    DockerRuleBuiler addBind(String bindString) {
        binds.add(bindString);
        return this;
    }
    public List<String> binds() {
        return binds;
    }
}