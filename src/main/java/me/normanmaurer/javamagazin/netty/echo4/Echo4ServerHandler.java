package me.normanmaurer.javamagazin.netty.echo4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundByteHandlerAdapter;


/**
 * {@link ChannelInboundByteHandlerAdapter} implementation die alle Daten die Sie empfängt
 * wieder and den Client zurueckschickt..
 */
@Sharable
public class Echo4ServerHandler extends ChannelInboundByteHandlerAdapter {

    // Sende empfangene Daten zu Client
    @Override
    public void inboundBufferUpdated(ChannelHandlerContext ctx, ByteBuf in) {
        ByteBuf out = ctx.nextOutboundByteBuffer();
        out.discardReadBytes();
        out.writeBytes(in);
        ctx.flush();
    }

    // Gebe StackTrace aus und schliesse die Verbindung
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}

