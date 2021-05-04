package org.example.server.service.impl.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Первым принимает поток байт. Данные принимает, и пробрасывает их дальше в виде строки.
 * Для того чтобы сделать такой класс-обработчик, наследуем его либо от ChannelInboundHandlerAdapter, либо от
 * SimpleChannelInboundHandler
 */

public class FirstIn extends SimpleChannelInboundHandler{ // можно передать тип данных, приходящий в обработчик, но в данном случае оставим по умолчанию <Object>

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx); // Метод вызывается, когда клиент подключился. Здесь можно делать действия по инициализации
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {   // под Object будет ByteBuff
        ByteBuf byteBuf = (ByteBuf) msg;
        StringBuilder builder = new StringBuilder();
        while (byteBuf.isReadable()){
            builder.append((char) byteBuf.readByte());
        }
        byteBuf.release(); // очистка byteBuf

        System.out.println("recieved " + builder.toString());
        ctx.fireChannelRead(builder.toString().trim()); //пробрасываем данные в виде строки дальше по pipeline
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        ctx.close();   // когда клиент отключился
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
