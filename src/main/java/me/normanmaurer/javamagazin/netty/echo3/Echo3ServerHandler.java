package me.normanmaurer.javamagazin.netty.echo3;

import io.netty.channel.ChannelHandler.Sharable;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * {@link SimpleChannelUpstreamHandler} implementation die alle Daten die Sie empfaengt
 * wieder an den Client zurueckschickt. 
 * 
 * Die Implementation beruht auf Netty 3.x.
 */
@Sharable
public class Echo3ServerHandler extends SimpleChannelUpstreamHandler {


    // Sende empfangene Daten zu Client
    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) {
        e.getChannel().write(e.getMessage());
    }

    // Gebe StackTrace aus und schliesse die Verbindung
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}