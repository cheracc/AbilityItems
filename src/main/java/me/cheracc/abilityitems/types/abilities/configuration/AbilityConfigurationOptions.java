package me.cheracc.abilityitems.types.abilities.configuration;

import me.cheracc.abilityitems.types.PluginConfig;
import me.cheracc.abilityitems.types.abilities.Ability;

import java.lang.reflect.Field;
import java.util.*;

public class AbilityConfigurationOptions {
    private final Ability ability;
    private final PluginConfig abilitiesConfig;
    private final Map<String, ConfigurationOption> configurationOptions;

    public AbilityConfigurationOptions(Ability ability, PluginConfig abilitiesConfig) {
        configurationOptions = new HashMap<>();
        this.ability = ability;
        this.abilitiesConfig = abilitiesConfig;
        loadOptionsMap();
    }

    public ConfigurationOption getValue(String configOption) {
       return configurationOptions.get(configOption);
    }

    public List<String> availableOptions() {
        return new ArrayList<>(configurationOptions.keySet());
    }

    private void loadOptionsMap() {
        for (Field f : getAllConfigurableFields()) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(ConfigOption.class)) {
                String configOption = fieldNameToConfigOption(f.getName());
                ConfigOptionType type = f.getAnnotation(ConfigOption.class).type();
                try {
                    configurationOptions.put(configOption, new ConfigurationOption(f.get(ability), type, configOption));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Field> getAllConfigurableFields() {
        List<Field> fields = new ArrayList<>();

        for (Field f : ability.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(ConfigOption.class))
                fields.add(f);
        }
        Class<?> clas = ability.getClass().getSuperclass();
        while (clas != null && clas.isAssignableFrom(Ability.class)) {
            for (Field f : clas.getDeclaredFields()) {
                if (f.isAnnotationPresent(ConfigOption.class))
                    fields.add(f);
            }
            clas = clas.getSuperclass();
        }
        return fields;
    }

    private String fieldNameToConfigOption(String string) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            Character c = string.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(" ");
                sb.append(Character.toLowerCase(c));
            } else
                sb.append(c);
        }
        return sb.toString();
    }

}
