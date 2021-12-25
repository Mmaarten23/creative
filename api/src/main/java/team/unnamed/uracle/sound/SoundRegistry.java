/*
 * This file is part of uracle, licensed under the MIT license
 *
 * Copyright (c) 2021 Unnamed Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package team.unnamed.uracle.sound;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Namespaced;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

/**
 * Represents a registry of {@link SoundEvent}, or
 * "sounds.json" in the resource-pack
 */
public class SoundRegistry implements Examinable, Namespaced {

    @Subst(Key.MINECRAFT_NAMESPACE)
    private final String namespace;
    private final Map<String, SoundEvent> sounds;

    private SoundRegistry(
            @Subst(Key.MINECRAFT_NAMESPACE) String namespace,
            Map<String, SoundEvent> sounds
    ) {
        requireNonNull(sounds, "sounds");
        this.namespace = requireNonNull(namespace, "namespace");
        this.sounds = unmodifiableMap(new HashMap<>(sounds));

        // let Key check if namespace is valid
        Key.key(namespace, "dummy");
    }

    @Override
    @Pattern("[a-z0-9_\\-.]+")
    public @NotNull String namespace() {
        return namespace;
    }

    public @Unmodifiable Map<String, SoundEvent> sounds() {
        return sounds;
    }

    @Override
    public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("namespace", namespace),
                ExaminableProperty.of("sounds", sounds)
        );
    }

    @Override
    public String toString() {
        return examine(StringExaminer.simpleEscaping());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoundRegistry that = (SoundRegistry) o;
        return namespace.equals(that.namespace)
                && sounds.equals(that.sounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, sounds);
    }

    /**
     * Creates a new registry from the given
     * properties
     *
     * @param namespace The registry namespace
     * @param sounds The registered sounds
     * @return A new sound registry instance
     */
    public static SoundRegistry of(
            String namespace,
            Map<String, SoundEvent> sounds
    ) {
        return new SoundRegistry(namespace, sounds);
    }

    /**
     * Creates a new registry from the given
     * sounds, using the default minecraft
     * namespace ({@link Key#MINECRAFT_NAMESPACE})
     *
     * @param sounds The registered sounds
     * @return A new sound registry instance
     */
    public static SoundRegistry of(
            Map<String, SoundEvent> sounds
    ) {
        return new SoundRegistry(Key.MINECRAFT_NAMESPACE, sounds);
    }

}