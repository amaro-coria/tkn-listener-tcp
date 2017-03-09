/**
 * Teknei 2016
 */
package com.teknei.service;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.teknei.service.client.ReceNaveRestClient;

import lombok.SneakyThrows;

/**
 * Mina configuration component
 * 
 * @author Jorge Amaro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class MinaConfig {

	/*
	 * Injected values
	 */
	@Value("${tkn.tcp.port}")
	private int port;
	@Autowired
	private ReceNaveRestClient clientReceNave;

	/**
	 * Post-construct method
	 */
	@PostConstruct
	private void postConstruct() {
		initMina();
	}

	/**
	 * Init mina server
	 */
	@SneakyThrows
	private void initMina() {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.setHandler(new MinaTCPHandler(clientReceNave));
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(port));
		initMinaLog(port);
	}

	private void initMinaLog(int port) {
		System.out.println("##############################################");
		System.out.println("   ###########      #####    ######   ");
		System.out.println("       ###        ####       ###  ### ");
		System.out.println("       ###       #####       ###  ###");
		System.out.println("       ###      ####         ######  ");
		System.out.println("       ###       ####        ###     ");
		System.out.println("       ###        #######    ###     ");
		System.out.println("Bound to port: " + port);
		System.out.println("##############################################");
	}

}
