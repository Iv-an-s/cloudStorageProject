package org.example.server.service.impl.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.server.factory.Factory;
import org.example.server.service.CommandDictionaryService;

public class SecondIn extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String command) throws Exception {
        CommandDictionaryService dictionaryService = Factory.getCommandDirectoryService();
        String commandResult = dictionaryService.processCommand(command); // commandResult необходимо выкинуть клиенту

        ctx.writeAndFlush(commandResult);
    }


}
