package handler;

import io.netty.channel.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServerSessionHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger channelNum = new AtomicInteger(0);

    private ConcurrentHashMap<String, AtomicInteger> ip2AtomicSum = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelNum.incrementAndGet();
        AtomicInteger ipAtomicInteger = ip2AtomicSum.computeIfAbsent(ctx.channel().toString(), k -> new AtomicInteger());
        ipAtomicInteger.incrementAndGet();
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelNum.decrementAndGet();
        AtomicInteger atomicInteger = ip2AtomicSum.get(ctx.channel().toString());
        if (atomicInteger == null) {
            return;
        }
        atomicInteger.decrementAndGet();
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
    }


}
