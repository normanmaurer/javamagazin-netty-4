package me.normanmaurer.javamagazin.netty.echo3;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * Echo Server auf Basis von Netty 3.
 */
public class Echo3Server {

    private final int port;

    public Echo3Server(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // Konfigurieren der Instanz
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new Echo3ServerHandler());
            }
        });

        try {
            // Binden des Servers 
            Channel channel = bootstrap.bind(new InetSocketAddress(port));
            channel.getCloseFuture().sync();
        } finally {
            // Freigeben aller Resourcen
            bootstrap.releaseExternalResources();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new Echo3Server(port).start();
    }
}