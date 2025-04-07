/*
 * Copyright (c) 2024 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/>.
 */
package megamek.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import megamek.client.ui.swing.util.PlayerColour;

class PlayerTest {

    @Test
    void testGetColorForPlayerDefault() {
        String playerName = "Test Player 1";
        Player player = new Player(0, playerName);
        assertEquals("<B><font color='8080b0'>" + playerName + "</font></B>", player.getColorForPlayer());
    }

    @Test
    void testGetColorForPlayerFuchsia() {
        String playerName = "Test Player 2";
        Player player = new Player(1, playerName);
        player.setColour(PlayerColour.FUCHSIA);
        assertEquals("<B><font color='f000f0'>" + playerName + "</font></B>", player.getColorForPlayer());
    }

    @Test
    void testConstructorAndBasicFields() {
        Player player = new Player(1, "Alice");
        assertEquals(1, player.getId());
        assertEquals("Alice", player.getName());
    }

    @Test
    void testMinefieldAddAndRemove() {
        Player player = new Player(1, "Alice");
        Coords coords = new Coords(0, 0); // coordonnée fictive
        Minefield mf = Minefield.createMinefield(coords, player.getId(), Minefield.TYPE_CONVENTIONAL, 10);

        player.addMinefield(mf);
        assertTrue(player.containsMinefield(mf), "Minefield should be present after adding");
        assertEquals(1, player.getMinefields().size(), "There should be one minefield");

        player.removeMinefield(mf);
        assertFalse(player.containsMinefield(mf), "Minefield should be removed");
        assertEquals(0, player.getMinefields().size(), "There should be no minefields");
    }

    @Test
    void testSetAndGetTeam() {
        Player player = new Player(2, "Bob");
        player.setTeam(3);
        assertEquals(3, player.getTeam());
    }

    @Test
    void testObserverFlag() {
        Player player = new Player(3, "Charlie");
        player.setObserver(true);
        assertTrue(player.isObserver());
        player.setObserver(false);
        assertFalse(player.isObserver());
    }

    @Test
    void testBotFlag() {
        Player player = new Player(4, "BotPlayer");
        player.setBot(true);
        assertTrue(player.isBot());
    }

    @Test
    void testGameMasterPermission() {
        Player player = new Player(5, "GMPlayer");
        player.setBot(false);
        player.setGameMaster(true);
        assertTrue(player.isGameMaster());
    }

    @Test
    void testEqualsAndHashCode() {
        Player p1 = new Player(10, "Duplicate");
        Player p2 = new Player(10, "OtherName");
        Player p3 = new Player(11, "Different");

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testCopyCreatesCloneWithSameData() {
        Player original = new Player(6, "CloneTest");
        original.setEmail("test@example.com");
        original.setTeam(2);
        original.setBot(false);
        original.setObserver(true);
        original.setGameMaster(true);

        Player copy = original.copy();

        assertEquals(original, copy);
        assertEquals(original.getEmail(), copy.getEmail());
        assertEquals(original.getTeam(), copy.getTeam());
        assertFalse(copy.isBot());
        assertTrue(copy.isObserver());
        assertTrue(copy.isGameMaster());
        assertNotSame(original, copy);
    }

    @Test
    void testIsEnemyOf() {
        Player p1 = new Player(1, "Player1");
        Player p2 = new Player(2, "Player2");

        p1.setTeam(1);
        p2.setTeam(2);
        assertTrue(p1.isEnemyOf(p2));

        p2.setTeam(1);
        assertFalse(p1.isEnemyOf(p2));
    }
}