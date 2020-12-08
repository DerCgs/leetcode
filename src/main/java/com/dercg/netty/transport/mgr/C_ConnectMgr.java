package com.dercg.netty.transport.mgr;

import com.dercg.netty.transport.codec.C_ClientEncoder;
import com.dercg.netty.transport.codec.C_ServerDecoder;
import com.dercg.netty.transport.timer.DefaultTimer;
import com.dercg.netty.transport.util.SystemTimeUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class C_ConnectMgr {
    private final EventGroupMgr eventGroupMgr;
    private final C_ClientSessionMgr clientSessionMgr;
    private final ProtoHandlerMgr protoHandlerMgr;
    private final List<C_ReconnectInfo> reconnects = new ArrayList<>();

    public C_ConnectMgr(EventGroupMgr eventGroupMgr, C_ClientSessionMgr clientSessionMgr, TimerMgr timerMgr, ProtoHandlerMgr protoHandlerMgr) {
        this.eventGroupMgr = eventGroupMgr;
        this.clientSessionMgr = clientSessionMgr;
        this.protoHandlerMgr = protoHandlerMgr;
        timerMgr.add(new DefaultTimer(clientSessionMgr::sendPing));
        timerMgr.add(new DefaultTimer(clientSessionMgr::checkTimeoutSession));
        timerMgr.add(new DefaultTimer(this::reConnect, 5000));
    }

    public C_ClientSessionMgr getClientSessionMgr() {
        return this.clientSessionMgr;
    }

    public ProtoHandlerMgr getProtoHandlerMgr() {
        return this.protoHandlerMgr;
    }

    public Channel connect(String ip, int port) {
        return doConnect(ip, port);
    }

    public Channel connect(String serviceName, String ip, int port) {
        try {
            String address = getAddress(ip, port);
            Channel channel = clientSessionMgr.getChannel(serviceName, address);

            if (channel != null) {
                return channel;
            }

            channel = doConnect(ip, port);

            if (channel != null) {
                C_ClientSessionInfo session = new C_ClientSessionInfo();
                session.setServerName(serviceName);
                session.setChannel(channel);
                session.setRemoteIp(ip);
                session.setRemotePort(port);
                session.setLastPingSec(SystemTimeUtil.getTimestamp());
                clientSessionMgr.addSession(serviceName, address, session);
            }
            return channel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onDisconnect(Channel channel) {
        if (clientSessionMgr.getSession(channel) != null) {
            C_ReconnectInfo reconnectInfo = new C_ReconnectInfo();
            reconnectInfo.setChannel(channel);
            reconnectInfo.setStartTime(SystemTimeUtil.getTimestamp());
            reconnects.add(reconnectInfo);
        }
    }

    private Channel doConnect(String ip, int port) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventGroupMgr.getWorkGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new C_ServerDecoder(C_ConnectMgr.this));
                        ch.pipeline().addLast(new C_ClientEncoder(C_ConnectMgr.this));
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_RCVBUF, 64 * 1024)
                .option(ChannelOption.SO_SNDBUF, 64 * 1024)
                .option(ChannelOption.SO_LINGER, 0);
        try {
            return bootstrap.connect(ip, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void reConnect() {
        for (Iterator<C_ReconnectInfo> itr = reconnects.iterator(); itr.hasNext(); ) {
            C_ReconnectInfo info = itr.next();
            long endTime = SystemTimeUtil.getTimestamp();
            int tryReconnectionTimeout = SessionMgr.headSecond;
            if (endTime - info.getStartTime() > tryReconnectionTimeout) {
                itr.remove();
                continue;
            }

            C_ClientSessionInfo session = clientSessionMgr.getSession(info.getChannel());
            if (session == null) {
                itr.remove();
                continue;
            }

            Channel newChannel = doConnect(session.getRemoteIp(), session.getRemotePort());
            if (newChannel != null) {
                itr.remove();
                clientSessionMgr.removeSession(info.getChannel());
                String address = getAddress(session.getRemoteIp(), session.getRemotePort());
                C_ClientSessionInfo newSession = new C_ClientSessionInfo();
                newSession.setServerName(session.getServerName());
                newSession.setChannel(newChannel);
                newSession.setRemoteIp(session.getRemoteIp());
                newSession.setRemotePort(session.getRemotePort());
                newSession.setLastPingSec(SystemTimeUtil.getTimestamp());
                clientSessionMgr.addSession(session.getServerName(), address, newSession);
            }
        }
    }

    private String getAddress(String ip, int port) {
        return ip + ":" + port;
    }
}
