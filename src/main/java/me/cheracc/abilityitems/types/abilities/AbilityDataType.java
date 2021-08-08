package me.cheracc.abilityitems.types.abilities;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Base64;

public class AbilityDataType implements PersistentDataType<byte[], Ability> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Ability> getComplexType() {
        return Ability.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Ability ability, @NotNull PersistentDataAdapterContext context) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(ability);
                return Base64.getEncoder().encode(baos.toByteArray());
        } catch (final IOException e) {
            return new byte[0];
        }
    }

    @Override
    public @NotNull Ability fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        final byte[] data = Base64.getDecoder().decode(primitive);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            Object o = ois.readObject();
            return (Ability) o;
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Ability("None", "");
        }
    }
}
