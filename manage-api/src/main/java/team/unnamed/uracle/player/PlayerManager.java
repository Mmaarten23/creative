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
package team.unnamed.uracle.player;

import java.util.UUID;

/**
 * Represents the object responsible for managing
 * online players in a Minecraft server
 *
 * @since 1.0.0
 */
public interface PlayerManager {

    /**
     * Determines whether a player with the
     * specified {@link UUID} has accepted
     * the server resource-pack or not
     *
     * <p>Always returns {@code false} for
     * invalid player uuids</p>
     *
     * @param playerId The player uuid
     * @return True if player has the resource-pack
     */
    boolean hasPack(UUID playerId);

    /**
     * Changes the "hasPack" state for the
     * specified {@code playerId}
     *
     * <p>Will not do anything if the specified
     * player uuid is invalid</p>
     *
     * @param playerId The player uuid
     * @param state The new "hasPack" state
     */
    void setHasPack(UUID playerId, boolean state);

}