package chapter2.part5;

/**
 * Ex2.5.10
 */
public class Version implements Comparable<Version> {
    private int major, minor, patch;

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public Version(String version) {
        String[] fields = version.split(".");
        major = Integer.parseInt(fields[0]);
        minor = Integer.parseInt(fields[1]);
        patch = Integer.parseInt(fields[2]);
    }

    public String toString() {
        return major + "." + minor + "." + patch;
    }

    public int compareTo(Version that) {
        if (this.major < that.major) {
            return -1;
        } else if (this.major > that.major) {
            return 1;
        } else if (this.minor < that.minor) {
            return -1;
        } else if (this.minor > that.minor) {
            return 1;
        } else if (this.patch < that.patch) {
            return -1;
        } else if (this.patch > that.patch) {
            return 1;
        } else {
            return 0;
        }
    }
}
