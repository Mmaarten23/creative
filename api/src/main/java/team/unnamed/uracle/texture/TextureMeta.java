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
package team.unnamed.uracle.texture;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.NotNull;
import team.unnamed.uracle.Element;
import team.unnamed.uracle.TreeWriter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Represents {@link Texture} meta-information that is applicable
 * for any texture type
 *
 * @since 1.0.0
 */
public class TextureMeta implements Element.Part, Examinable {

    /**
     * Causes the texture to blur when viewed
     * from close up
     */
    private final boolean blur;

    /**
     * Causes the texture to stretch instead of
     * tiling in cases where it otherwise would,
     * such as on the shadow
     */
    private final boolean clamp;

    /**
     * Custom mipmap values for the texture
     */
    private final int[] mipmaps;

    private TextureMeta(
            boolean blur,
            boolean clamp,
            int[] mipmaps
    ) {
        this.blur = blur;
        this.clamp = clamp;
        this.mipmaps = requireNonNull(mipmaps, "mipmaps");
    }

    /**
     * Determines whether the texture will be
     * blur-ed when viewed from close up
     *
     * @return True to blur texture
     */
    public boolean blur() {
        return blur;
    }

    /**
     * Determines whether the texture is stretched instead of
     * tiled
     *
     * @return True to clamp
     */
    public boolean clamp() {
        return clamp;
    }

    /**
     * Returns custom mipmap values for this
     * texture
     *
     * @return The custom mipmaps
     */
    public int[] mipmaps() {
        return mipmaps;
    }

    @Override
    public void write(TreeWriter.Context context) {
        context.writeBooleanField("blur", blur);
        context.writeBooleanField("clamp", clamp);
        context.writeKey("mipmaps");
        context.startArray();
        for (int i = 0; i < mipmaps.length; i++) {
            if (i != 0) {
                // write separator from previous
                // value and current value
                context.writeSeparator();
            }
            context.writeIntValue(mipmaps[i]);
        }
        context.endArray();
    }

    @Override
    public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("blur", blur),
                ExaminableProperty.of("clamp", clamp),
                ExaminableProperty.of("mipmaps", mipmaps)
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
        TextureMeta that = (TextureMeta) o;
        return blur == that.blur
                && clamp == that.clamp
                && Arrays.equals(mipmaps, that.mipmaps);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(blur, clamp);
        result = 31 * result + Arrays.hashCode(mipmaps);
        return result;
    }

    /**
     * Creates a new {@link TextureMeta} instance to
     * be applied to a {@link Texture}
     *
     * @param blur To make the texture blur
     * @param clamp To stretch the texture
     * @param mipmaps Custom texture mipmaps
     * @return A new texture metadata instance
     */
    public static TextureMeta of(
            boolean blur,
            boolean clamp,
            int[] mipmaps
    ) {
        return new TextureMeta(blur, clamp, mipmaps);
    }

}