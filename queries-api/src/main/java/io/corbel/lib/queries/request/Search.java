package io.corbel.lib.queries.request;

import java.util.Optional;
import java.util.Set;

/**
 * @author Francisco Sanchez
 */
public class Search {
    private Optional<Set<String>> fields;
    private String text;
    private boolean binded;

    public Search() {}

    public Search(String text) {
        this.text = text;
        fields = Optional.empty();
    }

    public Optional<Set<String>> getFields() {
        return fields;
    }

    public void setFields(Optional<Set<String>> fields) {
        this.fields = fields;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isBinded() {
        return binded;
    }

    public void setBinded(boolean binded) {
        this.binded = binded;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (binded ? 1231 : 1237);
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Search other = (Search) obj;
        if (binded != other.binded) {
            return false;
        }
        if (fields == null) {
            if (other.fields != null) {
                return false;
            }
        } else if (!fields.equals(other.fields)) {
            return false;
        }
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
            return false;
        }
        return true;
    }
}
