package mz.mzlib.cli;

public final class CliVersion {

    public String getVersionString()
    {
        return versions;
    }
    public boolean isPaper()
    {
        return true;
    }
    public final int version;
    public final String versions;

    public CliVersion(String versionString){
        this.versions = versionString;
        String[] versions = getVersionString().split("\\.", -1);
        version = Integer.parseInt(versions[1]) * 100 + (versions.length > 2 ? Integer.parseInt(versions[2]) : 0);
    }

    @Override
    public String toString() {
        return getVersionString();
    }
}
