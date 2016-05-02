/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.util.Objects;

/**
 * Class helps {@link obfuscating.ConstantPruner} to store information about
 * variables.
 *
 * @author Sergey
 */
public class Variable {

    private final int levelOfVisibility;

    private final String name;

    public Variable(int levelOfVisibility, String name) {
        this.levelOfVisibility = levelOfVisibility;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.levelOfVisibility;
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Variable other = (Variable) obj;
        if (this.levelOfVisibility != other.levelOfVisibility) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
