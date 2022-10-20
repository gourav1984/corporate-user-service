package co.user.api.models;

import java.util.Set;

public abstract class AbstractModel {

    public abstract boolean validateFiles(Set<FileModel> files);

    private String warning;

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

}
