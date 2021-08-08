package me.cheracc.abilityitems.types.abilities.configuration;

import org.bukkit.event.inventory.ClickType;

import java.util.Arrays;
import java.util.List;

public class ConfigurationOption {
    private Object variable;
    private final String configOptionName;
    private final ConfigOptionType type;

    public ConfigurationOption(Object variable, ConfigOptionType type, String configOptionName) {
        this.variable = variable;
        this.configOptionName = configOptionName;
        this.type = type;
    }

    public Object getValue() {
        return variable;
    }

    public String getValueAsString() {
        // TODO flesh this out to account for items, enums, etc.
        return variable.toString();
    }

    public void change(ClickType clickType) {
        switch (type) {
            case CYCLE:
                if (variable instanceof Boolean)
                    variable = !((Boolean) variable);
                if (variable instanceof Enum) {
                    List<Object> enums = Arrays.asList(variable.getClass().getEnumConstants());
                    int index = enums.indexOf(variable);
                    if (clickType.isLeftClick())
                        index++;
                    else
                        index--;
                    if (index > enums.size() - 1)
                        index = 0;
                    if (index < 0)
                        index = enums.size() - 1;
                    variable = enums.get(index);
                }
            case PERCENT:
            case MATERIAL:
            case LONG_STRING:
            case SHORT_STRING:
            case LARGE_INTEGER:
            case POTION_EFFECT:
            case SMALL_INTEGER:
                if (variable instanceof Integer) {
                    int value = (int) variable;
                    int multiplier = 1;

                    if (clickType.isShiftClick())
                        multiplier = 5;

                    if (clickType.isLeftClick())
                        value += multiplier;
                    else
                        value -= multiplier;
                    variable = value;
                }
            default:
        }
    }
}
