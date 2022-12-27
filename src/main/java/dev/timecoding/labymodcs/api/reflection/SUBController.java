package dev.timecoding.labymodcs.api.reflection;

import com.google.gson.JsonArray;
import dev.timecoding.labymodcs.CustomSubtitles;
import dev.timecoding.labymodcs.api.reflection.enums.SubVersion;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.EncoderException;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.nio.charset.Charset;

public class SUBController {

    private CustomSubtitles customSubtitles;
    private SubVersion version;

    public SUBController(CustomSubtitles customSubtitles, SubVersion version){
        this.customSubtitles = customSubtitles;
        this.version = version;
    }

    public void sendClientMessage( Player player, String key, JsonArray messageContent ) {
        switch(this.version){
            case v1_8:
                new SUB18(this.customSubtitles).sendClientMessage(player, key, messageContent);
                break;
            case v1_12:
                new SUB112(this.customSubtitles).sendClientMessage(player, key, messageContent);
                break;
            case v1_16:
                new SUB116(this.customSubtitles).sendClientMessage(player, key, messageContent);
                break;
        }
    }
}
