/**
 * Teknei 2016
 */
package com.teknei.service;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
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
	@Value("${tkn.listener.port}")
	private int port;
	@Value("${tkn.listener.type}")
	private String type;
	@Autowired
	private ReceNaveRestClient clientReceNave;
	private static final String TCP_TYPE = "TCP";
	private static final String UDP_TYPE="UDP";

	/**
	 * Post-construct method
	 */
	@PostConstruct
	private void postConstruct() {
		type = type.trim().toUpperCase();
		switch (type) {
		case TCP_TYPE:
			initMina();
			break;
		case UDP_TYPE:
			initMinaUdp();
			break;
		default:
			initMina();
			initMinaUdp();
			break;
		}
	}

	/**
	 * Init mina server
	 */
	@SneakyThrows
	private void initMina() {
		IoAcceptor acceptor = new NioSocketAcceptor();
		// acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.setHandler(new MinaTKNHandler(clientReceNave));
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(port));
		initMinaLog(port);
	}

	@SneakyThrows
	private void initMinaUdp() {
		NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
		acceptor.setHandler(new MinaTKNHandler(clientReceNave));
		DatagramSessionConfig dcfg = acceptor.getSessionConfig();
		dcfg.setReuseAddress(true);
		acceptor.bind(new InetSocketAddress(port));
		initMinaLogUDP(port);
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

	private void initMinaLogUDP(int port) {
		System.out.println("##############################################");
		System.out.println("    ###   ###   ######     ######   ");
		System.out.println("    ###   ###   #### ##    ###  ### ");
		System.out.println("    ###   ###   ###   ##   ###  ###");
		System.out.println("    ###   ###   ###   ##   ######  ");
		System.out.println("    ###   ###   ###  ##    ###     ");
		System.out.println("    #########   ######     ###     ");
		System.out.println("Bound to port: " + port);
		System.out.println("##############################################");
	}

}
