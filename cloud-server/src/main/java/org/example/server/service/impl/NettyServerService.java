package org.example.server.service.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.example.server.service.ServerService;
import org.example.server.service.impl.handler.CommandInboundHandler;
import org.example.server.service.impl.handler.FirstIn;
import org.example.server.service.impl.handler.FirstOut;
import org.example.server.service.impl.handler.SecondIn;

public class NettyServerService implements ServerService{ //todo сделать синглтон
    @Override
    public void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // пул тредов для обработки подключения клиентов. Для наших нагрузок здесь достаточно 1 треда
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // пул тредов для обработки потоков данных (через pipeline), количество потоков равно количеству процессоров *2

        //создаем сам сервер
        try{
            ServerBootstrap bootstrap = new ServerBootstrap(); // ServerBootstrap - предназначен для запуска Netty сервера
            // сервер необходимо настроить..
            bootstrap.group(bossGroup, workerGroup) // передаем группы (Thread pool -ы) на обработку
                    .channel(NioServerSocketChannel.class)  // подключаем к channel конкретную реализацию
            // нашего сервера. Их несколько. Есть интерфейс ServerSocketChannel, который имеет несклько имплементаций:
            //EpollServerSocketChannel - низкоуровневая реализация для Линукса. Сервер будет побыстрее
            //KQueueServerSocketChannel - для Mac
            //NioServerSocketChannel - общий. Поддерживает больше протоколов (TCP и UDP поддерживают все)
            //... далее нужно создать pipeline: new ChannelInitializer создается ДЛЯ КАЖДОГО ПОДКЛЮЧЕННОГО КЛИЕНТА!!!
                    .childHandler(new ChannelInitializer<SocketChannel>() {  // Создаем pipeline. В Netty префиксы Child обычно означают работу с клиентом
                        @Override                                            // ChannelInitializer позволяют пользователю сконфигурировать новый канал
                        protected void initChannel(SocketChannel channel){  // Реализуем метод initChannel
                            // initChannel принимает SocketChannel (Netty !), а SocketChannel в свою очередь позволяет создавать первоначальную версию нашего pipeline
                            channel.pipeline().addLast(new FirstOut(), new FirstIn(), new SecondIn());
//                            channel.pipeline().addLast(new StringEncoder(), new StringDecoder(), new CommandInboundHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(8189).sync(); // bind - связываем наш сервер с каким-то портом, который сервер будет слушать и обрабатывать
                                                // sync() - после того как вызвали sync, сервер запускается. Возвращает ChannelFuture, который позволяет делать
                                                // отложенную обработку
            System.out.println("Сервер запущен");
            future.channel().closeFuture().sync(); // sync на closeFuture означает то, что мы здесь будем ждать (блокирующая операция)

        }catch (Exception e){
            System.out.println("Server failed");
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
