package dev.timecoding.labymodcs.api.reflection;

import com.google.gson.JsonArray;
import dev.timecoding.labymodcs.CustomSubtitles;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.EncoderException;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.nio.charset.Charset;

public class SUB116 {

    private CustomSubtitles customSubtitles;

    public SUB116(CustomSubtitles customSubtitles){
        this.customSubtitles = customSubtitles;
    }

    public void sendClientMessage( Player player, String key, JsonArray messageContent ) {
        byte[] bytes = getBytes( key, messageContent.toString() );
        PacketDataSerializer pds = new PacketDataSerializer( Unpooled.wrappedBuffer( bytes ) );
        PacketPlayOutCustomPayload payloadPacket = new PacketPlayOutCustomPayload(MinecraftKey.a("labymod3:main"), pds );
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket( payloadPacket );
    }

    public byte[] getBytes( String messageKey, String messageContents ) {
        ByteBuf byteBuf = Unpooled.buffer();

        writeString( byteBuf, messageKey );
        writeString( byteBuf, messageContents );

        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes( bytes );

        return bytes;
    }

    private void writeVarIntToBuffer( ByteBuf buf, int input ) {
        while ( (input & -128) != 0 ) {
            buf.writeByte( input & 127 | 128 );
            input >>>= 7;
        }

        buf.writeByte( input );
    }

    private void writeString( ByteBuf buf, String string ) {
        byte[] abyte = string.getBytes( Charset.forName( "UTF-8" ) );

        if ( abyte.length > Short.MAX_VALUE ) {
            throw new EncoderException( "String too big (was " + string.length() + " bytes encoded, max " + Short.MAX_VALUE + ")" );
        } else {
            writeVarIntToBuffer( buf, abyte.length );
            buf.writeBytes( abyte );
        }
    }

}
