/*
 * This file is part of creative, licensed under the MIT license
 *
 * Copyright (c) 2021-2023 Unnamed Team
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
package team.unnamed.creative.overlay;

import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.metadata.overlays.OverlayEntry;

/**
 * Represents a resource-pack overlay. Overlays are sub-packs
 * applied over the "normal" contents of a pack that are applied
 * only if they support the client's pack format.
 *
 * <p>For example, if the overlay foo is applied, the file
 * {@code overlays/foo/assets/minecraft/textures/bar.png} will replace
 * the contents of {@code assets/minecraft/textures/bar.png}.</p>
 *
 * <p>Note that overlays can add and replace files, but not remove
 * them.</p>
 *
 * <p>Also note that overlays do not have pack metadata or
 * icon.</p>
 *
 * @since 1.1.0
 * @sincePackFormat 18
 * @sinceMinecraft 1.20.2
 */
@ApiStatus.NonExtendable
public interface Overlay extends ResourceContainer {

    /**
     * Gets the overlay's directory name.
     *
     * @return The overlay directory name.
     * @since 1.1.0
     * @sincePackFormat 18
     * @sinceMinecraft 1.20.2
     */
    @Subst("dir")
    @NotNull String directory();

    /**
     * Creates a new overlay object that will live in the given
     * directory name.
     *
     * @param directory The overlay directory name.
     * @return The created overlay.
     * @since 1.1.0
     * @sincePackFormat 18
     * @sinceMinecraft 1.20.2
     */
    static @NotNull Overlay create(final @NotNull @OverlayEntry.Directory String directory) {
        return new OverlayImpl(directory);
    }

}
