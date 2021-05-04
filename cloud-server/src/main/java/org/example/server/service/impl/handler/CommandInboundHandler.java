package org.example.server.service.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.server.factory.Factory;
import org.example.server.service.CommandDictionaryService;

public class CommandInboundHandler extends SimpleChannelInboundHandler <String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String command){
        CommandDictionaryService dictionaryService = Factory.getCommandDirectoryService();
        String commandResult = dictionaryService.processCommand(command);

        ctx.writeAndFlush(commandResult);

    }
}
