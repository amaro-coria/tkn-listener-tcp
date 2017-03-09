package com.teknei.service;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.teknei.service.client.ReceNaveRestClient;

import lombok.SneakyThrows;

@Component
public class MinaConfig {
	
	@Value("${tkn.tcp.port}")
	private int port;
	@Autowired
	private ReceNaveRestClient clientReceNave;
	private static final Logger log = LoggerFactory.getLogger(MinaConfig.class);
	@PostConstruct
	private void postConstruct(){
		initMina();
	}
	
	@SneakyThrows
	private void initMina(){
		IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        acceptor.setHandler( new MinaTCPHandler(clientReceNave) );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        acceptor.bind( new InetSocketAddress(port) );
        log.info("TCP Listener bind to port: {}", port);
	}

}
