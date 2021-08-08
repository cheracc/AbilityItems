package me.cheracc.abilityitems.abilities;

import me.cheracc.abilityitems.types.abilities.Ability;

public class Sentry extends Ability {
    public Sentry(String customName, String description) {
        super(customName, description);
        this.description = "Places a small sentry that performs some task until it expires or is destroyed";
    }
}
