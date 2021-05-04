package org.example.server.service.impl.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.StandardCharsets;

public class FirstOut extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        String resultCommand = msg.toString();
        System.out.println("Отправляем результат: " + resultCommand);
        // чтобы осуществить отправку, снова нужен ByteBuf

        ByteBuf byteBuf = ctx.alloc().buffer();                             // создаем буфер
        byteBuf.writeBytes(resultCommand.getBytes(StandardCharsets.UTF_8)); // записываем в него массив байт
        byteBuf.retain(); // переводим byteBuf  в режим чтения
        ctx.writeAndFlush(byteBuf);
    }
}
