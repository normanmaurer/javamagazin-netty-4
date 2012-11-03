package me.normanmaurer.javamagazin.netty.echo4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Echo Server auf Basis von Netty 4.
 */
public class Echo4Server {

    private final int port;

    public Echo4Server(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // Konfigurieren der Instanz
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(new NioEventLoopGroup(), new NioEventLoopGroup())
             .channel(NioServerSocketChannel.class)
             .localAddress(new InetSocketAddress(port))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(
                             new Echo4ServerHandler());
                 }
             });

            // Binden des Servers 
            ChannelFuture f = b.bind().sync();

            f.channel().closeFuture().sync();
        } finally {
            // Freigeben aller Resourcen
            b.shutdown();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new Echo4Server(port).start();
    }
}
