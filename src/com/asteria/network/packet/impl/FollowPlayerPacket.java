package com.asteria.network.packet.impl;

import java.util.Arrays;

import com.asteria.game.World;
import com.asteria.game.character.player.Player;
import com.asteria.network.ByteOrder;
import com.asteria.network.DataBuffer;
import com.asteria.network.packet.PacketDecoder;

/**
 * The packet sent from the client when a player tries to follow another player.
 * 
 * @author lare96 <http://www.rune-server.org/members/lare96/>
 */
public final class FollowPlayerPacket extends PacketDecoder {

    @Override
    public void decode(Player player, int opcode, int size, DataBuffer buf) {
        if (player.getViewingOrb() != null)
            return;

        int index = buf.getShort(false, ByteOrder.LITTLE);
        Player follow = World.getPlayers().get(index);

        if (follow == null || !follow.getPosition().isViewableFrom(player.getPosition()) || follow.equals(player))
            return;
        Arrays.fill(player.getSkillEvent(), false);
        player.getMovementQueue().follow(follow);
    }
}
